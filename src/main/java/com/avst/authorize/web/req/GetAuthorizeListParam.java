package com.avst.authorize.web.req;

import com.avst.authorize.common.utils.Page;

public class GetAuthorizeListParam extends Page{

    private String clientName;
    private String username;
    private String sqcode;
    private String factory;
    private String batypessid;

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSqcode() {
        return sqcode;
    }

    public void setSqcode(String sqcode) {
        this.sqcode = sqcode;
    }

    public String getBatypessid() {
        return batypessid;
    }

    public void setBatypessid(String batypessid) {
        this.batypessid = batypessid;
    }
}
