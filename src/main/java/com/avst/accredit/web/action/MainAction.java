package com.avst.accredit.web.action;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainAction {

    @RequestMapping({"/", "index"})
    public ModelAndView Home(Model model){
        model.addAttribute("title", "首页系统");
        return new ModelAndView("index", "getIndex", model);
    }



}
