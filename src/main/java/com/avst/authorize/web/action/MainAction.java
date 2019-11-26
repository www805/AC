package com.avst.authorize.web.action;

import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.GetAuthorizeListParam;
import com.avst.authorize.web.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainAction {

    @RequestMapping({"/", "main", "index"})
    public ModelAndView Home(Model model){
        model.addAttribute("title", "授权系统");
        return new ModelAndView("main", "getMain", model);
    }

    @RequestMapping("/admin")
    public ModelAndView admin(Model model){
        model.addAttribute("title", "后台授权管理系统");
        return new ModelAndView("admin", "getAdmin", model);
    }




}
