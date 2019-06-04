package com.chryl.service;

import com.chryl.response.error.ResponseException;
import com.chryl.service.model.UserModel;
import com.chryl.sso.SsoUserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created By Chr on 2019/6/3.
 */
public interface SsoService {

    String doSso(HttpServletResponse response, UserModel userModel) throws ResponseException;

    SsoUserModel loginCheck(HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
