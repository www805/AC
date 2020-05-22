package com.avst.authorize.web.vo;

import com.avst.authorize.common.entity.SQCodeStatisticsEntity;

import java.util.List;

/**
 * @Auther: zhuang
 * @Date: 2020/5/21 0021 17:26
 * @Description:
 */
public class GetYearStatisticsVO {

    private List<SQCodeStatisticsEntity> userSQCodeCount;//申请人统计
    private List<SQCodeStatisticsEntity> companySQCodeCount;//公司统计
    private List<SQCodeStatisticsEntity> typeSQCodeCount;//类型统计
    private List<SQCodeStatisticsEntity> branchSQCodeCount;//分支统计
    private List<SQCodeStatisticsEntity> oemSQCodeCount;//oem统计
    private List<SQCodeStatisticsEntity> clientSQCodeCount;//客户端统计

    public List<SQCodeStatisticsEntity> getUserSQCodeCount() {
        return userSQCodeCount;
    }

    public void setUserSQCodeCount(List<SQCodeStatisticsEntity> userSQCodeCount) {
        this.userSQCodeCount = userSQCodeCount;
    }

    public List<SQCodeStatisticsEntity> getCompanySQCodeCount() {
        return companySQCodeCount;
    }

    public void setCompanySQCodeCount(List<SQCodeStatisticsEntity> companySQCodeCount) {
        this.companySQCodeCount = companySQCodeCount;
    }

    public List<SQCodeStatisticsEntity> getTypeSQCodeCount() {
        return typeSQCodeCount;
    }

    public void setTypeSQCodeCount(List<SQCodeStatisticsEntity> typeSQCodeCount) {
        this.typeSQCodeCount = typeSQCodeCount;
    }

    public List<SQCodeStatisticsEntity> getBranchSQCodeCount() {
        return branchSQCodeCount;
    }

    public void setBranchSQCodeCount(List<SQCodeStatisticsEntity> branchSQCodeCount) {
        this.branchSQCodeCount = branchSQCodeCount;
    }

    public List<SQCodeStatisticsEntity> getOemSQCodeCount() {
        return oemSQCodeCount;
    }

    public void setOemSQCodeCount(List<SQCodeStatisticsEntity> oemSQCodeCount) {
        this.oemSQCodeCount = oemSQCodeCount;
    }

    public List<SQCodeStatisticsEntity> getClientSQCodeCount() {
        return clientSQCodeCount;
    }

    public void setClientSQCodeCount(List<SQCodeStatisticsEntity> clientSQCodeCount) {
        this.clientSQCodeCount = clientSQCodeCount;
    }
}
