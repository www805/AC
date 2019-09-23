package com.avst.accredit.web.action;

import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.web.req.GetAccreditListParam;
import com.avst.accredit.web.req.UserParam;
import com.avst.accredit.web.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MainService mainService;

    @RequestMapping({"/", "main"})
    public ModelAndView Home(Model model){
        model.addAttribute("title", "首页系统");
        return new ModelAndView("main", "getMain", model);
    }

    /**
     * 获取所有授权记录
     * @param param
     * @return
     */
    @RequestMapping("/getAccreditList")
    public RResult getAccreditList(@RequestBody GetAccreditListParam param){
        RResult result = new RResult();
        result = mainService.getAccreditList(result, param);
        return result;
    }


}
