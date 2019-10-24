package com.avst.authorize.common.entity;

import com.avst.authorize.common.utils.sq.SQEntity;

import java.io.Serializable;

public class SQEntityPlus extends SQEntity implements Serializable {

    private static final long serialVersionUID = 3465873159777481144L;

    private String ssid;

    private String state = "1";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
