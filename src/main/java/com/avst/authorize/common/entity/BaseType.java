package com.avst.authorize.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.List;

@TableName("base_type")
public class BaseType extends Model<BaseType> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String typename;
    private String typecode;
    private Integer type;
    private Integer ordernum;
    private String ssid;
    private String string1;
    private String string2;
    private Integer integer1;
    private Integer integer2;

    @TableField(exist = false)
    private List<BaseGninfo> baseGninfo;

    public List<BaseGninfo> getBaseGninfo() {
        return baseGninfo;
    }

    public void setBaseGninfo(List<BaseGninfo> baseGninfo) {
        this.baseGninfo = baseGninfo;
    }

    public String getTypecode() {
        return typecode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
