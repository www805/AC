package com.avst.authorize.web.service;


import com.avst.authorize.common.cache.BaseGnInfoCache;
import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.BaseType;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.mapper.BaseGnInfoMapper;
import com.avst.authorize.web.mapper.BaseTypeMapper;
import com.avst.authorize.web.req.GetBaseTypeListParam;
import com.avst.authorize.web.vo.GetBaseTypeListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BaseTypeService {

    @Autowired
    private BaseTypeMapper baseTypeMapper;

    @Autowired
    private BaseGnInfoMapper baseGnInfoMapper;

    public RResult getBaseTypeList(RResult result, GetBaseTypeListParam param) {

        GetBaseTypeListVO baseTypeListVO = new GetBaseTypeListVO();

        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(param.getTypename())){
            ew.like("typename", param.getTypename());
        }

        ew.orderBy("ordernum", false);

        Integer count = baseTypeMapper.selectCount(ew);
        param.setRecordCount(count);

        com.baomidou.mybatisplus.plugins.Page<BaseType> page = new Page<>(param.getCurrPage(), param.getPageSize());
        List<BaseType> sqCacheList = baseTypeMapper.selectPage(page, ew);


        baseTypeListVO.setPagelist(sqCacheList);
        baseTypeListVO.setPageparam(param);

        result.changeToTrue(baseTypeListVO);
        return result;
    }


    public RResult getBaseTypeByssid(RResult result, GetBaseTypeListParam param) {

        BaseType baseType = new BaseType();
        baseType.setSsid( param.getSsid());

        baseType = baseTypeMapper.selectOne(baseType);

        result.changeToTrue(baseType);
        return result;
    }

    @Transactional
    public RResult deleteBaseTypeByssid(RResult result, GetBaseTypeListParam param) {

        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        EntityWrapper<BaseGninfo> ew2 = new EntityWrapper<>();
        ew2.eq("btypessid", param.getSsid());

        Integer gnUpdate = baseGnInfoMapper.delete(ew2);
        Integer delete = baseTypeMapper.delete(ew);

        result.changeToTrue(delete);
        return result;
    }

    public RResult addBaseType(RResult result, GetBaseTypeListParam param) {

        BaseType baseType = new BaseType();
        baseType.setTypename(param.getTypename());
        baseType.setTypecode(param.getTypecode());
        baseType.setOrdernum(param.getOrdernum());
        baseType.setSsid(OpenUtil.getUUID_32());

        boolean insert = baseType.insert();
        if (insert) {
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(insert);

        return result;
    }

    public RResult updateBaseType(RResult result, GetBaseTypeListParam param) {

        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        BaseType baseType = new BaseType();
        baseType.setTypename(param.getTypename());
        baseType.setTypecode(param.getTypecode());
        baseType.setOrdernum(param.getOrdernum());

        boolean update = baseType.update(ew);
        if (update) {
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(update);

        return result;
    }
}
