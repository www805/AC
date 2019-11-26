package com.avst.authorize.web.vo;

import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.BaseType;
import com.avst.authorize.web.req.GetBaseGnInfoListParam;
import com.avst.authorize.web.req.GetBaseTypeListParam;

import java.util.List;

public class GetBaseGnInfoListVO {

    private GetBaseGnInfoListParam pageparam;
    private List<BaseGninfo> pagelist;

    public GetBaseGnInfoListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetBaseGnInfoListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<BaseGninfo> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<BaseGninfo> pagelist) {
        this.pagelist = pagelist;
    }
}
