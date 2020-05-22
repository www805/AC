package com.avst.authorize.web.vo;

/**
 * @Auther: zhuang
 * @Date: 2020/5/21 0021 13:57
 * @Description:
 */
public class GetServerBaseStatisticsVO {

    //获取分支数量
    private Integer branchCount;
    //获取OEM版本数量
    private Integer oemCount;
    //获取授权功能数量
    private Integer sqgnCount;
    //获取全部授权数量
    private Integer sqAllCount;

    public Integer getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    public Integer getOemCount() {
        return oemCount;
    }

    public void setOemCount(Integer oemCount) {
        this.oemCount = oemCount;
    }

    public Integer getSqgnCount() {
        return sqgnCount;
    }

    public void setSqgnCount(Integer sqgnCount) {
        this.sqgnCount = sqgnCount;
    }

    public Integer getSqAllCount() {
        return sqAllCount;
    }

    public void setSqAllCount(Integer sqAllCount) {
        this.sqAllCount = sqAllCount;
    }
}
