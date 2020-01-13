package com.avst.authorize.web.service;


import com.avst.authorize.common.cache.BaseGnInfoCache;
import com.avst.authorize.common.entity.BaseGnType;
import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.mapper.BaseGnInfoMapper;
import com.avst.authorize.web.mapper.BaseGnTypeMapper;
import com.avst.authorize.web.req.GetBaseGnTypeListParam;
import com.avst.authorize.web.vo.GetBaseGnTypeListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BaseGnTypeService {

    @Autowired
    private BaseGnTypeMapper baseGnTypeMapper;

    @Autowired
    private BaseGnInfoMapper baseGnInfoMapper;

    public RResult getBaseGnTypeList(RResult result, GetBaseGnTypeListParam param) {

        GetBaseGnTypeListVO baseTypeListVO = new GetBaseGnTypeListVO();

        EntityWrapper<BaseGnType> ew = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(param.getTypename())){
            ew.like("typename", param.getTypename());
        }

        ew.orderBy("ordernum", false);

        Integer count = baseGnTypeMapper.selectCount(ew);
        param.setRecordCount(count);

        Page<BaseGnType> page = new Page<>(param.getCurrPage(), param.getPageSize());
        List<BaseGnType> sqCacheList = baseGnTypeMapper.selectPage(page, ew);


        baseTypeListVO.setPagelist(sqCacheList);
        baseTypeListVO.setPageparam(param);

        result.changeToTrue(baseTypeListVO);
        return result;
    }


    public RResult getBaseGnTypeByssid(RResult result, GetBaseGnTypeListParam param) {

        BaseGnType baseGnType = new BaseGnType();
        baseGnType.setSsid( param.getSsid());

        baseGnType = baseGnTypeMapper.selectOne(baseGnType);

        result.changeToTrue(baseGnType);
        return result;
    }

    @Transactional
    public RResult deleteBaseGnTypeByssid(RResult result, GetBaseGnTypeListParam param) {

        EntityWrapper<BaseGnType> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        EntityWrapper<BaseGninfo> ew2 = new EntityWrapper<>();
        ew2.eq("btypessid", param.getSsid());

        Integer gnUpdate = baseGnInfoMapper.delete(ew2);
        Integer delete = baseGnTypeMapper.delete(ew);

        LogUtil.intoLog(1, this.getClass(), "删除一条授权功能类型：bool=" + delete + "  ssid=" + param.getSsid());

        result.changeToTrue(delete);
        return result;
    }

    public RResult addBaseGnType(RResult result, GetBaseGnTypeListParam param) {

        EntityWrapper<BaseGnType> ew = new EntityWrapper<>();
        ew.eq("typename", param.getTypename().trim())
                .or()
                .eq("typecode", param.getTypecode().trim());

        List<BaseGnType> baseGnTypes = baseGnTypeMapper.selectList(ew);
        if (baseGnTypes.size() > 0) {
            LogUtil.intoLog(1, this.getClass(), "该功能类型名称或功能类型代码已经存在！");
            result.setMessage("该功能类型名称或功能类型代码已经存在！");
            return result;
        }

        BaseGnType baseGnType = new BaseGnType();
        baseGnType.setTypename(param.getTypename().trim());
        baseGnType.setTypecode(param.getTypecode().trim());
        baseGnType.setType(param.getType());
        baseGnType.setOrdernum(param.getOrdernum());
        baseGnType.setSsid(OpenUtil.getUUID_32());

        boolean insert = baseGnType.insert();
        if (insert) {
            LogUtil.intoLog(1, this.getClass(), "成功添加一条授权功能类型：" + baseGnType.toString());
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(insert);

        return result;
    }

    public RResult updateBaseGnType(RResult result, GetBaseGnTypeListParam param) {

        EntityWrapper<BaseGnType> eww = new EntityWrapper<>();
        eww.eq("typename", param.getTypename().trim())
                .and()
                .ne("ssid",param.getSsid())
                .or()
                .eq("typecode",param.getTypecode().trim())
                .and()
                .ne("ssid",param.getSsid());

        List<BaseGnType> typeList = baseGnTypeMapper.selectList(eww);
        if (typeList.size() > 0) {
            LogUtil.intoLog(1, this.getClass(), "该功能类型名称或功能类型代码已经存在！");
            result.setMessage("该功能类型名称或功能类型代码已经存在！");
            return result;
        }

        EntityWrapper<BaseGnType> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        BaseGnType baseGnType = new BaseGnType();
        baseGnType.setTypename(param.getTypename().trim());
        baseGnType.setTypecode(param.getTypecode().trim());
        baseGnType.setType(param.getType());
        baseGnType.setOrdernum(param.getOrdernum());

        boolean update = baseGnType.update(ew);
        if (update) {
            LogUtil.intoLog(1, this.getClass(), "成功修改一条授权功能类型：" + baseGnType.toString());
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(update);

        return result;
    }
}
