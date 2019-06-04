package com.chryl.sso;

import com.chryl.po.UserInfo;

import java.io.Serializable;

/**
 * Created By Chr on 2019/6/4.
 */
public class SsoVo extends UserInfo implements Serializable {

    private static final long serialVersionUID = 8993081644201602709L;
    private String userVersion;


    public String getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(String userVersion) {
        this.userVersion = userVersion;
    }
}
