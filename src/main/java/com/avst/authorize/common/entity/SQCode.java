package com.avst.authorize.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;


@TableName("ac_sqcode")
public class SQCode extends Model<SQCode>{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;//授权名称
    private String sqcode;//授权码
    private String realpath;//真实文件存放地址
    private String sqentityssid;//关联授权信息ssid
    private String startTime;//授权开始时间
    private Integer sqDay;
    private String ssid;
    private String string1;
    private String string2;
    private Integer integer1;
    private Integer integer2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSqcode() {
        return sqcode;
    }

    public void setSqcode(String sqcode) {
        this.sqcode = sqcode;
    }

    public String getRealpath() {
        return realpath;
    }

    public void setRealpath(String realpath) {
        this.realpath = realpath;
    }

    public String getSqentityssid() {
        return sqentityssid;
    }

    public void setSqentityssid(String sqentityssid) {
        this.sqentityssid = sqentityssid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getSqDay() {
        return sqDay;
    }

    public void setSqDay(Integer sqDay) {
        this.sqDay = sqDay;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
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

    @Override
    protected Serializable pkVal() {
        return this.ssid;
    }
}
