package com.avst.authorize.web.mapper;

import com.avst.authorize.common.entity.SQCode;
import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.entity.YearEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SQCodeMapper extends BaseMapper<SQCode> {


    public List<YearEntity> getYearStatistics(@Param("ew") EntityWrapper ew);

}
