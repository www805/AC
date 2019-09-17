package com.avst.accredit.web.action;

import com.avst.accredit.common.entity.User;
import com.avst.accredit.common.utils.DateUtil;
import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.web.req.UserParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginAction {


    @RequestMapping("/login")
    public ModelAndView gotoLongin(Model model) {
        model.addAttribute("title", "用户登录");
        return new ModelAndView("/login", "loginModel", model);
    }


    /**
     * 登录接口
     * @param param
     * @return
     */
    @PostMapping("/loginCaChe")
    public RResult loginCaChe(@RequestBody @Validated UserParam param) {

        RResult<User> result = new RResult();

        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(param.getLoginaccount(), param.getPassword());

        //执行登录方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            result.setMessage("用户名不存在");
            return result;
        } catch (IncorrectCredentialsException e) {
            result.setMessage("用户名或密码错误");
            return result;
        }

        result.changeToTrue();
        return result;
    }


    @RequestMapping("/logout")
    public RResult logout() {
        RResult<User> result = new RResult();

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        result.changeToTrue();
        return result;
    }

}
