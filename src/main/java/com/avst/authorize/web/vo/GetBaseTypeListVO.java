package com.avst.authorize.web.vo;

import com.avst.authorize.common.entity.BaseType;
import com.avst.authorize.web.req.GetBaseTypeListParam;

import java.util.List;

public class GetBaseTypeListVO {

    private GetBaseTypeListParam pageparam;
    private List<BaseType> pagelist;

    public GetBaseTypeListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetBaseTypeListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<BaseType> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<BaseType> pagelist) {
        this.pagelist = pagelist;
    }
}
