package com.avst.accredit.web.action;

import com.avst.accredit.common.utils.DateUtil;
import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.web.service.AccreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AccreditAction {

    @Autowired
    private AccreditService accreditService;

    /***
     * 授权文件上传接口
     * @return
     */
    @PostMapping(value = "/uploadBytxt")
    @ResponseBody
    public RResult uploadBytxt(@RequestParam("file") MultipartFile file) {
        RResult result = new RResult();
        accreditService.uploadBytxt(result, file);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

}
