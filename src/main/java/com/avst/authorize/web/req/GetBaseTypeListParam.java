package com.avst.authorize.web.req;

import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.config.check.Update;
import com.avst.authorize.common.utils.Page;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class GetBaseTypeListParam extends Page {

    @NotBlank(message = "类型名称不能为空", groups = {Create.class, Update.class})
    private String typename;//类型名称
    @NotBlank(message = "类型代码不能为空", groups = {Create.class, Update.class})
    private String typecode;//类型代码
    @DecimalMin(value = "0",message = "是否为单选框不能为空", groups = {Create.class, Update.class})
    private Integer type;//是否为单选框
    @DecimalMin(value = "0", message = "排序必须为数字")
    private Integer ordernum;//排序

    @NotBlank(message = "ssid不能为空", groups = {Delete.class, Update.class})
    private String ssid;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
