package com.avst.authorize.web.action;


import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.config.check.Update;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.GetBaseTypeListParam;
import com.avst.authorize.web.service.BaseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 授权类型管理
 */
@RestController
@RequestMapping("base")
public class BaseTypeAction {

    @Autowired
    private BaseTypeService baseTypeService;

    /**
     * 获取所有授权类型
     * @param param
     * @return
     */
    @RequestMapping("/getBaseTypeList")
    public RResult getBaseTypeList(@RequestBody GetBaseTypeListParam param){
        RResult result = new RResult();
        result = baseTypeService.getBaseTypeList(result, param);
        return result;
    }

    /**
     * 通过ssid获取授权类型
     * @param param
     * @return
     */
    @RequestMapping("/getBaseTypeByssid")
    public RResult getBaseTypeByssid(@RequestBody @Validated(Delete.class) GetBaseTypeListParam param){
        RResult result = new RResult();
        result = baseTypeService.getBaseTypeByssid(result, param);
        return result;
    }

    /**
     * 新增授权类型
     * @param param
     * @return
     */
    @RequestMapping("/addBaseType")
    public RResult addBaseType(@RequestBody @Validated(Create.class) GetBaseTypeListParam param){
        RResult result = new RResult();
        result = baseTypeService.addBaseType(result, param);
        return result;
    }

    /**
     * 修改授权类型
     * @param param
     * @return
     */
    @RequestMapping("/updateBaseType")
    public RResult updateBaseType(@RequestBody @Validated(Update.class) GetBaseTypeListParam param){
        RResult result = new RResult();
        result = baseTypeService.updateBaseType(result, param);
        return result;
    }

    /**
     * 删除一条授权类型
     * @param param
     * @return
     */
    @RequestMapping("/deleteBaseTypeByssid")
    public RResult deleteBaseTypeByssid(@RequestBody @Validated(Delete.class) GetBaseTypeListParam param){
        RResult result = new RResult();
        result = baseTypeService.deleteBaseTypeByssid(result, param);
        return result;
    }

    @RequestMapping("/tobasetype")
    public ModelAndView admin(Model model){
        model.addAttribute("title", "授权类型管理");
        return new ModelAndView("base/basetype", "getBasetype", model);
    }



}
