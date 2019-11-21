package com.avst.authorize.common.entity;

import com.avst.authorize.common.utils.sq.SQEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.List;

@TableName("ac_sqentityplus")
public class SQEntityPlus extends SQEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 3465873159777481144L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username; //申请人名称
    private Integer sqsize; //授权台数
    private String companyname; //公司名称
    private String companymsg; //公司简介
    private String ssid; //唯一ID

    private Integer state = 1; //状态

    @TableField(exist = false)
    private List<SQCode> sqCodeList; //授权码

    private String string1;
    private String string2;
    private Integer integer1;
    private Integer integer2;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SQCode> getSqCodeList() {
        return sqCodeList;
    }

    public void setSqCodeList(List<SQCode> sqCodeList) {
        this.sqCodeList = sqCodeList;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSqsize() {
        return sqsize;
    }

    public void setSqsize(Integer sqsize) {
        this.sqsize = sqsize;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanymsg() {
        return companymsg;
    }

    public void setCompanymsg(String companymsg) {
        this.companymsg = companymsg;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public Integer getInteger1() {
        return integer1;
    }

    public void setInteger1(Integer integer1) {
        this.integer1 = integer1;
    }

    public Integer getInteger2() {
        return integer2;
    }

    public void setInteger2(Integer integer2) {
        this.integer2 = integer2;
    }
}

