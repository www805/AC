package com.avst.authorize.web.mapper;

import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.entity.SQCodeStatisticsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SQEntityMapper extends BaseMapper<SQEntityPlus> {


    public SQEntityPlus getFindByssid(@Param("ssid") String ssid);

    public Integer getSQCount(@Param("ew") EntityWrapper ew);

    public List<SQEntityPlus> getSQListPage(Page page, @Param("ew") EntityWrapper ew);

    public List<SQEntityPlus> getSQList(@Param("ew") EntityWrapper ew);

    public List<SQCodeStatisticsEntity> getUserSQCodeCount(@Param("ew") EntityWrapper ew);

    public List<SQCodeStatisticsEntity> getCompanynameSQCodeCount(@Param("ew") EntityWrapper ew);

    public List<SQCodeStatisticsEntity> getTypenameSQCodeCount(@Param("ew") EntityWrapper ew);


}
