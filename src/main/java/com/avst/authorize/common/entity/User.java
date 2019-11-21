package com.avst.authorize.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
 
import java.io.Serializable;
import java.util.Date;

@TableName("ac_user")
public class User extends Model<User> implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 3465873159777481144L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
 
    /**
     * 登录账号
     */
    private String loginaccount;
 
    /**
     * 用户密码
     */
    private String password;
 
    /**
     * 用户状态：1正常; 0禁用;默认1
     */
    private Integer userbool;
 
    /**
     * 最后一次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastlogintime;
 
    private String ssid;
 
 
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getLoginaccount() {
        return loginaccount;
    }
 
    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserbool() {
        return userbool;
    }

    public void setUserbool(Integer userbool) {
        this.userbool = userbool;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }
 
    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }
 
    public String getSsid() {
        return ssid;
    }
 
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
 
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginaccount='" + loginaccount + '\'' +
                ", password='" + password + '\'' +
                ", userbool=" + userbool +
                ", lastlogintime=" + lastlogintime +
                ", ssid='" + ssid + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.ssid;
    }
}