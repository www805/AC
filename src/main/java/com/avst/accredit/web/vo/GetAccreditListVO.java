package com.avst.accredit.web.vo;

import com.avst.accredit.common.utils.sq.SQEntity;
import com.avst.accredit.web.req.GetAccreditListParam;

import java.util.List;

public class GetAccreditListVO {

    private GetAccreditListParam pageparam;

    private List<SQEntity> pagelist;

    public GetAccreditListParam getPageparam() {
        return pageparam;
    }

    public void setPageparam(GetAccreditListParam pageparam) {
        this.pageparam = pageparam;
    }

    public List<SQEntity> getPagelist() {
        return pagelist;
    }

    public void setPagelist(List<SQEntity> pagelist) {
        this.pagelist = pagelist;
    }
}
