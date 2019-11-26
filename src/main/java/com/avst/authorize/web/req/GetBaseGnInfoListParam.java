package com.avst.authorize.web.req;

import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.config.check.Update;
import com.avst.authorize.common.utils.Page;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GetBaseGnInfoListParam extends Page {

    @NotBlank(message = "授权代号不能为空", groups = {Create.class, Update.class})
    private String name;//授权代号
    @NotBlank(message = "功能标题不能为空", groups = {Create.class, Update.class})
    private String title;//功能标题
    @NotBlank(message = "关联类型ssid不能为空", groups = {Create.class, Update.class})
    private String btypessid;//关联类型ssid

    @NotBlank(message = "ssid不能为空", groups = {Delete.class, Update.class})
    private String ssid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBtypessid() {
        return btypessid;
    }

    public void setBtypessid(String btypessid) {
        this.btypessid = btypessid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
