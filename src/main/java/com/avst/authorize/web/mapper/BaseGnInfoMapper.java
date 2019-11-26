package com.avst.authorize.web.mapper;

import com.avst.authorize.common.entity.BaseGninfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseGnInfoMapper extends BaseMapper<BaseGninfo> {


    public List<BaseGninfo> getBaseGnInfoList(Page page, @Param("ew") EntityWrapper ew);

}
