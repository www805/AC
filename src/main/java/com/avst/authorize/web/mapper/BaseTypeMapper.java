package com.avst.authorize.web.mapper;

import com.avst.authorize.common.entity.BaseType;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

public interface BaseTypeMapper extends BaseMapper<BaseType> {


    public List<BaseType> getBaseType();

}
