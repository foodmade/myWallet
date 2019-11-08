package com.mywallet.web.controller.api.suport;

import com.alibaba.fastjson.JSONObject;
import com.mywallet.commom.AppProperties;
import com.mywallet.model.Users;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * BaseController
 *
 * @author linapex
 */
public abstract class BaseController {

    @Autowired
    protected AppProperties appProperties; //属性工具

    protected JSONObject newJson(Object data) {
        return newJson("200", "操作成功！", data);
    }

    protected JSONObject newJson(String code, String msg) {
        return newJson(code, msg, null);
    }

    protected JSONObject newJson(String code, String msg, Object data) {
        return new JSONObject().fluentPut("code", code).fluentPut("msg", msg).fluentPut("data", data);
    }

    protected Users getCurrentUsers(HttpSession session) {
        return (Users) session.getAttribute("users");
    }

}
