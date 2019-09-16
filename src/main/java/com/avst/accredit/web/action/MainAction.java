package com.avst.accredit.web.action;

import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.web.req.UserParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class MainAction {

    @RequestMapping({"/", "main"})
    public ModelAndView Home(Model model){
        model.addAttribute("title", "首页系统");
        return new ModelAndView("main", "getMain", model);
    }


    @RequestMapping("/ai")
    public RResult getTest(){
        int i = 1 / 0;
        RResult result = new RResult();
        return result;
    }


    @RequestMapping("/getai")
    public RResult getAi(@RequestBody @Valid UserParam userParam){

        RResult result = new RResult();
        result.setData(userParam);
        result.setSuccess();

        return result;
    }

}
