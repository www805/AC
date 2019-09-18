package com.avst.accredit.web.req;

import com.avst.accredit.common.utils.Page;

public class GetAccreditListParam extends Page {

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
