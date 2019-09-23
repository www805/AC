package com.avst.accredit.web.action;

import com.avst.accredit.common.config.check.Create;
import com.avst.accredit.common.config.check.Delete;
import com.avst.accredit.common.utils.DateUtil;
import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.web.req.GetAccreditParam;
import com.avst.accredit.web.service.AccreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AccreditAction {

    @Autowired
    private AccreditService accreditService;


    /**
     * 添加授权
     * @param param
     * @return
     */
    @PostMapping(value = "/addAccredit")
    public RResult addAccredit(@RequestBody @Validated(Create.class) GetAccreditParam param) {
        RResult result = new RResult();
        accreditService.addAccredit(result, param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 删除一条授权记录
     * @param param
     * @return
     */
    @RequestMapping(value = "/delAccredit")
    public RResult delAccredit(@RequestBody @Validated(Delete.class) GetAccreditParam param) {
        RResult result = new RResult();
        accreditService.delAccredit(result, param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 获取全部权限
     * @return
     */
    @PostMapping(value = "/getPrivilege")
    public RResult getPrivilege() {
        RResult result = new RResult();
        accreditService.getPrivilege(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /***
     * 授权文件上传接口
     * @return
     */
    @PostMapping(value = "/uploadBytxt")
    public RResult uploadBytxt(@RequestParam("file") MultipartFile file) {
        RResult result = new RResult();
        accreditService.uploadBytxt(result, file);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
