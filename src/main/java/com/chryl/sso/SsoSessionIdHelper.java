package com.chryl.sso;

/**
 * Created By Chr on 2019/6/3.
 */
public class SsoSessionIdHelper {
    /**
     * make client sessionId:userId_userVersion
     *
     * @param ssoUserModel
     * @return
     */
    public static String makeSessionId(SsoUserModel ssoUserModel) {
        //userid_userVersion
        String sessionId = ssoUserModel.getUserId().concat("_").concat(ssoUserModel.getUserVersion());
        return sessionId;
    }

    /**
     * parse storeKey from sessionId:userId
     *
     * @param sessionId
     * @return
     */
    public static String parseStoreKey(String sessionId) {
        if (sessionId != null && sessionId.indexOf("_") > -1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length == 2
                    && sessionIdArr[0] != null
                    && sessionIdArr[0].trim().length() > 0) {
                String userId = sessionIdArr[0].trim();
                return userId;
            }
        }
        return null;
    }

    /**
     * parse version from sessionId:userVersion
     *
     * @param sessionId
     * @return
     */
    public static String parseVersion(String sessionId) {
        if (sessionId != null && sessionId.indexOf("_") > -1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length == 2
                    && sessionIdArr[1] != null
                    && sessionIdArr[1].trim().length() > 0) {
                String version = sessionIdArr[1].trim();
                return version;
            }
        }
        return null;
    }
}
