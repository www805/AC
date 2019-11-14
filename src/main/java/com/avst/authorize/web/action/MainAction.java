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

    @Autowired
    private MainService mainService;

    @RequestMapping({"/", "main", "index"})
    public ModelAndView Home(Model model){
        model.addAttribute("title", "首页系统");
        return new ModelAndView("main", "getMain", model);
    }

    @RequestMapping("/admin")
    public ModelAndView admin(Model model){
        model.addAttribute("title", "后台管理");
        return new ModelAndView("admin", "getAdmin", model);
    }

    /**
     * 获取所有授权记录
     * @param param
     * @return
     */
    @RequestMapping("/getAuthorizeList")
    public RResult getAuthorizeList(@RequestBody GetAuthorizeListParam param){
        RResult result = new RResult();
        result = mainService.getAuthorizeList(result, param);
        return result;
    }


}
