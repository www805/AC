package com.avst.authorize.web.action;

import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.GetAuthorizeListParam;
import com.avst.authorize.web.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: zhuang
 * @Date: 2020/5/20 0020 09:56
 * @Description:
 * 授权统计页面
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsAction {

    @Autowired
    private StatisticsService statisticsService;


    @RequestMapping({"/", "", "index"})
    public ModelAndView gotoStatistics(Model model) {
        model.addAttribute("title", "授权统计");
        return new ModelAndView("statistics/sqtjindex", "mainModel", model);
    }

    /**
     * 获取笔录系统基本信息统计
     * @return
     */
    @RequestMapping("/getServerBaseStatistics")
    public RResult getServerBaseStatistics(){
        RResult result = new RResult();
        result = statisticsService.getServerBaseStatistics(result);
        return result;
    }


    /**
     * 获取申请人排行榜
     * @return
     */
    @RequestMapping("/getUsernamePaihb")
    public RResult getUsernamePaihb(){
        RResult result = new RResult();
        result = statisticsService.getUsernamePaihb(result);
        return result;
    }


    /**
     * 最近一年授权统计
     * @return
     */
    @RequestMapping("/getYearStatistics")
    public RResult getYearStatistics(){
        RResult result = new RResult();
        result = statisticsService.getYearStatistics(result);
        return result;
    }


    /**
     * 其他的授权统计
     * @return
     */
    @RequestMapping("/getElesStatistics")
    public RResult getElesStatistics(){
        RResult result = new RResult();
        result = statisticsService.getElesStatistics(result);
        return result;
    }

}
