package com.avst.authorize.web.req;

import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class GetAuthorizeParam {

    @NotBlank(message = "申请人名称不能为空", groups = Create.class)
    private String username;
//    @NotBlank(message = "公司名称不能为空", groups = Create.class)
    private String companyname;
    @NotEmpty(message = "单位名称不能为空", groups = Create.class)
    private String clientName;
    @NotEmpty(message = "单位简称不能为空", groups = Create.class)
    private String unitCode;
    @NotEmpty(message = "授权类型不能为空", groups = Create.class)
    private String batypessid;
    private Integer sqDay;
    private Integer sortNum;
    private Integer sqsize;
    private Boolean foreverBool;
//    @NotEmpty(message = "授权服务类型不能为空", groups = Create.class)
    private String serverType;
    @NotEmpty(message = "授权码不能为空", groups = Create.class)
    private String cpuCode;
    @NotEmpty(message = "授权功能列表不能为空", groups = Create.class)
    private String gnlist;
    @NotBlank(message = "ssid不能为空", groups = Delete.class)
    private String ssid;
//    @NotBlank(message = "公司简介不能为空", groups = Create.class)
    private String companymsg;

    public String getCompanyname() {
        return companyname;
    }

    public Integer getSqsize() {
        return sqsize;
    }

    public void setSqsize(Integer sqsize) {
        this.sqsize = sqsize;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanymsg() {
        return companymsg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCompanymsg(String companymsg) {
        this.companymsg = companymsg;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getSqDay() {
        return sqDay;
    }

    public void setSqDay(Integer sqDay) {
        this.sqDay = sqDay;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Boolean getForeverBool() {
        return foreverBool;
    }

    public void setForeverBool(Boolean foreverBool) {
        this.foreverBool = foreverBool;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getCpuCode() {
        return cpuCode;
    }

    public void setCpuCode(String cpuCode) {
        this.cpuCode = cpuCode;
    }

    public String getGnlist() {
        return gnlist;
    }

    public void setGnlist(String gnlist) {
        this.gnlist = gnlist;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBatypessid() {
        return batypessid;
    }

    public void setBatypessid(String batypessid) {
        this.batypessid = batypessid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
