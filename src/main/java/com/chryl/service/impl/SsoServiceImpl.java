package com.chryl.service.impl;

import com.alibaba.fastjson.JSON;
import com.chryl.response.error.EnumError;
import com.chryl.response.error.ResponseException;
import com.chryl.service.SsoService;
import com.chryl.service.model.UserModel;
import com.chryl.sso.Conf;
import com.chryl.sso.CookieUtil;
import com.chryl.sso.SsoSessionIdHelper;
import com.chryl.sso.SsoUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created By Chr on 2019/6/3.
 */
@Service
public class SsoServiceImpl implements SsoService {
    private static int redisExpireMinute = 1440;    // 1440 minite, 24 hour
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String doSso(HttpServletResponse response, UserModel userModel) throws ResponseException {
        //1.make sso user
        SsoUserModel ssoUserModel = packSsoModel(userModel);
        //2.make session id:userId_userVersion
        String sessionId = SsoSessionIdHelper.makeSessionId(ssoUserModel);
        // 3、login, store storeKey + cookie sessionId:storeKey为userid
        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new ResponseException(EnumError.SSO_CANCLE);
        }
        //存入redis,存入coolie
        stringRedisTemplate.opsForValue().set(
                redisKey(storeKey),//
                JSON.toJSONString(ssoUserModel),//存储jsonString
                redisExpireMinute,//
                TimeUnit.MINUTES);//分钟
        //存cookie
        //@param ifRemember    true: cookie not expire, false: expire when browser close （server cookie）
        CookieUtil.set(response, Conf.SSO_SESSIONID, sessionId, true);
        // 4、return, redirect sessionId

        return sessionId;

    }

    //1.验证cookie,有coolie:直接返回,没有remove,重新set,刷新redis
    @Override
    public SsoUserModel loginCheck(HttpServletRequest request, HttpServletResponse response) {
        //验证cookie,有就返回,无就remove,set
        String cookieSessionId = CookieUtil.getValue(request, Conf.SSO_SESSIONID);
        //验证cookie和redis
        SsoUserModel ssoUserModel = checkCookieAndRedis(cookieSessionId);//userid_userversion
        if (ssoUserModel != null) {//存在ssoModel直接返回
            return ssoUserModel;
        }
        //cookie已过期,
        SsoUserModel ssoUserModel1 = removeAndSetCookie(request, response, ssoUserModel);

        if (ssoUserModel1 != null) {
            return ssoUserModel1;
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //清除cookie
        String cookieSessionId = CookieUtil.getValue(request, Conf.SSO_SESSIONID);
        if (cookieSessionId == null) {
            return;
        }
        //清除redis
        String storeKey = SsoSessionIdHelper.parseStoreKey(cookieSessionId);
        //redisKey
        String redisKey = redisKey(storeKey);
        if (redisKey != null) {
            stringRedisTemplate.delete(redisKey);
//            SsoLoginStore.remove(storeKey);
        }
        CookieUtil.remove(request, response, Conf.SSO_SESSIONID);
    }

    //cookie已过期,remove-set
    public SsoUserModel removeAndSetCookie(HttpServletRequest request, HttpServletResponse response, SsoUserModel ssoUserModel) {
        // remove old cookie
        CookieUtil.remove(request, response, Conf.SSO_SESSIONID);
        // set new cookie
        String paramSessionId = request.getParameter(Conf.SSO_SESSIONID);
        ssoUserModel = checkCookieAndRedis(paramSessionId);
        if (ssoUserModel != null) {
            CookieUtil.set(response, Conf.SSO_SESSIONID, paramSessionId, false);    // expire when browser close （client cookie）
            return ssoUserModel;
        }
        return null;
    }

    //验证redis和cookie
    public SsoUserModel checkCookieAndRedis(String cookieSessionId) {//userid_userversion
        //storeKey为userid
        String storeKey = SsoSessionIdHelper.parseStoreKey(cookieSessionId);
        if (storeKey == null) {//没有该cookie值
            return null;
        }
        //有cookie值
        //redis:根据key取value
        String redisJsonStrValue = stringRedisTemplate.opsForValue().get(redisKey(storeKey));//redis存储的为jsonString格式

        //redis 存ssoModel------------------------------
        SsoUserModel ssoUserModel = JSON.parseObject(redisJsonStrValue, SsoUserModel.class);
        if (ssoUserModel != null) {
            String userVersion = SsoSessionIdHelper.parseVersion(cookieSessionId);
            if (ssoUserModel.getUserVersion().equals(userVersion)) {//对比redis中的version和cookie中的version是否一样

                // After the expiration time has passed half, Auto refresh(过期时间过半刷新)
                if ((System.currentTimeMillis() - ssoUserModel.getExpireFreshTime()) > ssoUserModel.getExpireMinute() * 60 * 1000 >> 1) {//redis存的minute
                    ssoUserModel.setExpireFreshTime(System.currentTimeMillis());
                    stringRedisTemplate.opsForValue().set(storeKey, //
                            ssoUserModel.toString(), //
                            1440,//分钟
                            TimeUnit.MINUTES);
                }
                return ssoUserModel;
            }
        }
        return null;
    }


    //redisKey:sso_sessionid#userid
    private static String redisKey(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }

    //组装SsoModel
    private SsoUserModel packSsoModel(UserModel userModel) {
        SsoUserModel ssoUserModel = new SsoUserModel();
        ssoUserModel.setUserId(userModel.getUserId());
        ssoUserModel.setUserName(userModel.getUserName());
        ssoUserModel.setUserPhone(userModel.getUserPhone());
        ssoUserModel.setUserVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUserModel.setExpireFreshTime(System.currentTimeMillis());
        ssoUserModel.setExpireMinute(redisExpireMinute);
        return ssoUserModel;
    }

    public static void main(String args[]) {
        SsoUserModel ssoUserModel = new SsoUserModel();
        ssoUserModel.setUserVersion("asdsadsadasdasdad");
        ssoUserModel.setExpireFreshTime(System.currentTimeMillis());
        ssoUserModel.setExpireMinute(redisExpireMinute);

        String s = JSON.toJSONString(ssoUserModel);
        SsoUserModel ssoUserModel1 = JSON.parseObject(s, SsoUserModel.class);
        System.out.println(ssoUserModel1);
    }
}
