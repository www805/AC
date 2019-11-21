package com.avst.authorize.web.mapper;

import com.avst.authorize.common.entity.SQEntityPlus;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SQEntityMapper extends BaseMapper<SQEntityPlus> {


    public SQEntityPlus getFindByssid(@Param("ssid") String ssid);

}
