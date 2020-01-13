package com.avst.authorize.web.mapper;

import com.avst.authorize.common.entity.BaseGnType;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

public interface BaseGnTypeMapper extends BaseMapper<BaseGnType> {


    public List<BaseGnType> getBaseNgType();

}
