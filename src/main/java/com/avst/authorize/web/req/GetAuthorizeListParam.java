package com.avst.authorize.web.req;

import com.avst.authorize.common.utils.Page;

public class GetAuthorizeListParam{

    private String clientName;
    private String username;



    private Integer pagesize_d=10;
    private Integer pageSize = pagesize_d;// 显示条数
    private Integer recordCount = 0;// 总条
    private Integer pageCount = 0;//总页数
    private Integer currPage_d = 1;//当前页
    private Integer currPage = currPage_d;//当前页
    private boolean hasPrevious=false;//是否有上一页
    private boolean hasNext=false;//是否有下一页
    private Integer begin = 0;//查询开始的行数


    public GetAuthorizeListParam() {
        super();
    }

    public GetAuthorizeListParam(Integer pageSize, Integer recordCount, Integer pageCount,
                Integer currPage, boolean hasPrevious, boolean hasNext,
                Integer begin) {
        super();
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        this.pageCount = pageCount;
        this.currPage = currPage;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.begin = begin;
    }

    /**
     * 获取总的页数
     * @return
     */
    public Integer getPageCount() {
        Integer size = this.recordCount / this.pageSize;
        Integer mod = recordCount % pageSize;
        if (mod != 0) {
            size++;
        }
        return this.recordCount == 0 ? 1 : size;
    }

    /**
     * 是否有上一页
     *
     * @return
     */
    public boolean thisIsHasPrevious() {
        if(null==currPage){
            currPage=currPage_d;
        }
        if (this.currPage > 1) {
            return true;
        }
        return false;
    }

    /**
     * 是否有下一页
     *
     * @return
     */
    public boolean thisIsHasNext() {
        //不是最后一页
        if (this.currPage < this.getPageCount()) {
            return true;
        }
        return false;
    }

    public Integer getPageSize() {
        if(null==pageSize){
            pageSize=pagesize_d;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getCurrPage() {
        if(null==currPage||currPage<=0){
            currPage=currPage_d;
        }
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }
    public boolean getHasPrevious() {
        return this.thisIsHasPrevious()?true:false;
    }
    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean getHasNext(){
        return this.thisIsHasNext()?true:false;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }


    public Integer getBegin() {
        return (this.getCurrPage()-1)*getPageSize();
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }



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
