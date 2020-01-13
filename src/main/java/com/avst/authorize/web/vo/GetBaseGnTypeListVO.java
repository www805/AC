package com.avst.authorize.web.vo;

import com.avst.authorize.common.entity.BaseGnType;
import com.avst.authorize.web.req.GetBaseGnTypeListParam;

import java.util.List;

public class GetBaseGnTypeListVO {

    private GetBaseGnTypeListParam pageparam;
    private List<BaseGnType> pagelist;

    public GetBaseGnTypeListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetBaseGnTypeListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<BaseGnType> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<BaseGnType> pagelist) {
        this.pagelist = pagelist;
    }
}
