package com.avst.authorize.web.action;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 授权用户管理
 */
@RestController
@RequestMapping("/ac")
public class UserAction {





    @RequestMapping("/toUserList")
    public ModelAndView admin(Model model){
        model.addAttribute("title", "授权账号管理");
        return new ModelAndView("web/userlist", "getUserList", model);
    }
    



}
