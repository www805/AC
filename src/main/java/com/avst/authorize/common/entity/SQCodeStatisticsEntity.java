package com.avst.authorize.common.entity;

/**
 * @Auther: zhuang
 * @Date: 2020/5/21 0021 14:27
 * @Description:
 * 授权统计
 */
public class SQCodeStatisticsEntity {

    private String name;//授权名
    private Integer value = 0;//授权数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
