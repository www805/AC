package com.avst.authorize.web.vo;

import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.req.GetAuthorizeListParam;

import java.util.List;

public class GetAuthorizeListVO {

    private GetAuthorizeListParam pageparam;

    private List<SQEntity> pagelist;

    public GetAuthorizeListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetAuthorizeListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<SQEntity> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<SQEntity> pagelist) {
        this.pagelist = pagelist;
    }
}
