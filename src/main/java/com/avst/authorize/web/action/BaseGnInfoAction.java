package com.avst.authorize.web.action;


import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.config.check.Update;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.GetBaseGnInfoListParam;
import com.avst.authorize.web.service.BaseGnInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 授权功能管理
 */
@RestController
@RequestMapping("/base")
public class BaseGnInfoAction {

    @Autowired
    private BaseGnInfoService baseGnInfoService;

    /**
     * 获取所有授权功能
     * @param param
     * @return
     */
    @RequestMapping("/getBaseGnInfoList")
    public RResult getBaseGnInfoList(@RequestBody GetBaseGnInfoListParam param){
        RResult result = new RResult();
        result = baseGnInfoService.getBaseGnInfoList(result, param);
        return result;
    }

    /**
     * 通过ssid获取授权功能
     * @param param
     * @return
     */
    @RequestMapping("/getBaseGnInfoByssid")
    public RResult getBaseGnInfoByssid(@RequestBody @Validated(Delete.class) GetBaseGnInfoListParam param){
        RResult result = new RResult();
        result = baseGnInfoService.getBaseGnInfoByssid(result, param);
        return result;
    }

    /**
     * 新增授权功能
     * @param param
     * @return
     */
    @RequestMapping("/addBaseGnInfo")
    public RResult addBaseGnInfo(@RequestBody @Validated(Create.class) GetBaseGnInfoListParam param){
        RResult result = new RResult();
        result = baseGnInfoService.addBaseGnInfo(result, param);
        return result;
    }

    /**
     * 修改授权功能
     * @param param
     * @return
     */
    @RequestMapping("/updateBaseGnInfo")
    public RResult updateBaseGnInfo(@RequestBody @Validated(Update.class) GetBaseGnInfoListParam param){
        RResult result = new RResult();
        result = baseGnInfoService.updateBaseGnInfo(result, param);
        return result;
    }

    /**
     * 删除一条授权功能
     * @param param
     * @return
     */
    @RequestMapping("/deleteBaseGnInfoByssid")
    public RResult deleteBaseGnInfoByssid(@RequestBody @Validated(Delete.class) GetBaseGnInfoListParam param){
        RResult result = new RResult();
        result = baseGnInfoService.deleteBaseGnInfoByssid(result, param);
        return result;
    }



    @RequestMapping("/tobasegninfo")
    public ModelAndView admin(Model model){
        model.addAttribute("title", "授权功能管理");
        return new ModelAndView("base/baseGnInfo", "getBaseGninfo", model);
    }
    



}
