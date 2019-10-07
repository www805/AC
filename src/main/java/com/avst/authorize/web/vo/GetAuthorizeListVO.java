package com.avst.authorize.web.vo;

import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.web.req.GetAuthorizeListParam;

import java.util.List;

public class GetAuthorizeListVO {

    private GetAuthorizeListParam pageparam;

    private List<SQEntityPlus> pagelist;

    public GetAuthorizeListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetAuthorizeListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<SQEntityPlus> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<SQEntityPlus> pagelist) {
        this.pagelist = pagelist;
    }
}
