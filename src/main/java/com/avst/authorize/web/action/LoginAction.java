package com.avst.authorize.web.action;

import com.avst.authorize.common.config.check.UserCheckSequence;
import com.avst.authorize.common.entity.User;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.LoginCheckParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
public class LoginAction {
 
 
    @RequestMapping("/login")
    public ModelAndView gotoLongin(Model model) {
        model.addAttribute("title", "用户登录");
        return new ModelAndView("login", "loginModel", model);
    }
 
 
    /**
     * 登录接口
     * @param param
     * @return
     */
    @PostMapping("/loginCaChe")
    public RResult loginCaChe(@RequestBody @Validated({UserCheckSequence.class}) LoginCheckParam param) {
 
        RResult<User> result = new RResult();
 
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(param.getLoginaccount(), param.getPassword());
 
        //执行登录方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            result.setMessage("用户名或密码错误");
            return result;
        } catch (IncorrectCredentialsException e) {
            result.setMessage("用户名或密码错误");
            return result;
        }

        //修改最后登录时间
        User user = (User) subject.getPrincipal();
        user.setLastlogintime(new Date());
        boolean updateById_bool = user.updateById();
        LogUtil.intoLog(this.getClass(),"登录成功，修改最后登录时间--"+updateById_bool);

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