package com.avst.authorize.web.req;

import com.avst.authorize.common.config.check.PasswordCheck;
import com.avst.authorize.common.config.check.UserNameCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
 
/**
 * 登录验证类
 */
public class LoginCheckParam {
 
    @NotBlank(message = "用户名不能为空", groups = {UserNameCheck.class})
    @Size(min = 5, max = 12, message = "用户名长度必须是5-12位之间")
    private String loginaccount;
 
    @NotBlank(message = "密码不能为空", groups = {PasswordCheck.class})
    private String password;
 
    public String getLoginaccount() {
        return loginaccount;
    }
 
    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    @Override
    public String toString() {
        return "LoginCheckParam{" +
                "loginaccount='" + loginaccount + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}