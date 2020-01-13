package com.avst.authorize.web.req;

import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.config.check.Update;
import com.avst.authorize.common.utils.Page;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

/**
 * @Auther: zhuang
 * @Date: 2020/1/11 14:59
 * @Description:
 */
public class GetBaseTypeListParam extends Page{

    @NotBlank(message = "类型名称不能为空", groups = {Create.class, Update.class})
    private String typename;
    @NotBlank(message = "类型ssid不能为空", groups = {Delete.class, Update.class})
    private String ssid;
    @DecimalMin(value = "0", message = "排序必须为数字")
    private Integer ordernum;//排序

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }
}
