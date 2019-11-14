package com.avst.authorize.web.req;

import com.avst.authorize.common.utils.Page;

public class GetAuthorizeListParam extends Page {

    private String clientName;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
