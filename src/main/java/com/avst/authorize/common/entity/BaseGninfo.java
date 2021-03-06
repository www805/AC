package com.avst.authorize.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

@TableName("base_gninfo")
public class BaseGninfo extends Model<BaseGninfo> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String title;
    private String bgntypessid;
    private String ssid;
    private String string1;
    private String string2;
    private Integer integer1;
    private Integer integer2;

    @TableField(exist = false)
    private BaseGnType baseGnType;

    public BaseGnType getBaseGnType() {
        return baseGnType;
    }

    public void setBaseGnType(BaseGnType baseGnType) {
        this.baseGnType = baseGnType;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBgntypessid() {
        return bgntypessid;
    }

    public void setBgntypessid(String bgntypessid) {
        this.bgntypessid = bgntypessid;
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

    @Override
    public String toString() {
        return "BaseGninfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", bgntypessid='" + bgntypessid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", string1='" + string1 + '\'' +
                ", string2='" + string2 + '\'' +
                ", integer1=" + integer1 +
                ", integer2=" + integer2 +
                ", baseGnType=" + baseGnType +
                '}';
    }
}
