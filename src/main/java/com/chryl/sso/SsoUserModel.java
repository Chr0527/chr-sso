package com.chryl.sso;

import com.chryl.po.UserInfo;

import java.io.Serializable;

/**
 * Created By Chr on 2019/5/31.
 */
public class SsoUserModel extends UserInfo implements Serializable {

    private static final long serialVersionUID = 4144559772332050803L;

    // field
    private String userVersion;
    private int expireMinute;
    private long expireFreshTime;


    // get set

    public String getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(String userVersion) {
        this.userVersion = userVersion;
    }

    public int getExpireMinute() {
        return expireMinute;
    }

    public void setExpireMinute(int expireMinute) {
        this.expireMinute = expireMinute;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }
}
