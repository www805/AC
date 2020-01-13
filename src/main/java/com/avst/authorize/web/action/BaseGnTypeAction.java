package com.avst.authorize.web.action;


import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.config.check.Update;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.GetBaseGnTypeListParam;
import com.avst.authorize.web.service.BaseGnTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 授权功能类型管理
 */
@RestController
@RequestMapping("base")
public class BaseGnTypeAction {

    @Autowired
    private BaseGnTypeService baseGnTypeService;

    /**
     * 获取所有授权功能类型
     * @param param
     * @return
     */
    @RequestMapping("/getBaseGnTypeList")
    public RResult getBaseGnTypeList(@RequestBody GetBaseGnTypeListParam param){
        RResult result = new RResult();
        result = baseGnTypeService.getBaseGnTypeList(result, param);
        return result;
    }

    /**
     * 通过ssid获取授权功能类型
     * @param param
     * @return
     */
    @RequestMapping("/getBaseGnTypeByssid")
    public RResult getBaseGnTypeByssid(@RequestBody @Validated(Delete.class) GetBaseGnTypeListParam param){
        RResult result = new RResult();
        result = baseGnTypeService.getBaseGnTypeByssid(result, param);
        return result;
    }

    /**
     * 新增授权功能类型
     * @param param
     * @return
     */
    @RequestMapping("/addBaseGnType")
    public RResult addBaseGnType(@RequestBody @Validated(Create.class) GetBaseGnTypeListParam param){
        RResult result = new RResult();
        result = baseGnTypeService.addBaseGnType(result, param);
        return result;
    }

    /**
     * 修改授权功能类型
     * @param param
     * @return
     */
    @RequestMapping("/updateBaseGnType")
    public RResult updateBaseGnType(@RequestBody @Validated(Update.class) GetBaseGnTypeListParam param){
        RResult result = new RResult();
        result = baseGnTypeService.updateBaseGnType(result, param);
        return result;
    }

    /**
     * 删除一条授权功能类型
     * @param param
     * @return
     */
    @RequestMapping("/deleteBaseGnTypeByssid")
    public RResult deleteBaseGnTypeByssid(@RequestBody @Validated(Delete.class) GetBaseGnTypeListParam param){
        RResult result = new RResult();
        result = baseGnTypeService.deleteBaseGnTypeByssid(result, param);
        return result;
    }

    @RequestMapping("/tobasegntype")
    public ModelAndView tobasegntype(Model model){
        model.addAttribute("title", "授权功能类型管理");
        return new ModelAndView("base/basegnType", "getBasegntype", model);
    }



}
