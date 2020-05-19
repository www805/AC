package com.avst.authorize.web.req;

import com.avst.authorize.common.config.check.Create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Auther: zhuang
 * @Date: 2020/5/18 0018 16:20
 * @Description:
 */
public class UpdateAuthorizeTimeParam {

    @NotBlank(message = "授权码不能为空")
    private String xqCpuCode;
    @Min(value = 0,message = "续期时间必须大于0")
    private Integer xqSqDay;

    public String getXqCpuCode() {
        return xqCpuCode;
    }

    public void setXqCpuCode(String xqCpuCode) {
        this.xqCpuCode = xqCpuCode;
    }

    public Integer getXqSqDay() {
        return xqSqDay;
    }

    public void setXqSqDay(Integer xqSqDay) {
        this.xqSqDay = xqSqDay;
    }
}
