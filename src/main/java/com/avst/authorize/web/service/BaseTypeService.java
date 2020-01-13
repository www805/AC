package com.avst.authorize.web.service;

import com.avst.authorize.common.cache.BaseGnInfoCache;
import com.avst.authorize.common.entity.BaseGnType;
import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.BaseType;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.mapper.BaseTypeMapper;
import com.avst.authorize.web.req.GetBaseTypeListParam;
import com.avst.authorize.web.vo.GetBaseTypeListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.List;

/**
 * @Auther: zhuang
 * @Date: 2020/1/11 14:58
 * @Description:
 */
@Service
public class BaseTypeService {

    @Autowired
    private BaseTypeMapper baseTypeMapper;


    public RResult getBaseTypeList(RResult result, GetBaseTypeListParam param) {

        GetBaseTypeListVO vo = new GetBaseTypeListVO();


        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(param.getTypename())){
            ew.like("typename", param.getTypename());
        }

        ew.orderBy("ordernum", false);

        Integer count = baseTypeMapper.selectCount(ew);
        param.setRecordCount(count);

        Page<BaseType> page = new Page<>(param.getCurrPage(), param.getPageSize());
        List<BaseType> sqCacheList = baseTypeMapper.selectPage(page, ew);


        vo.setPagelist(sqCacheList);
        vo.setPageparam(param);

        result.changeToTrue(vo);

        return result;
    }

    public RResult getBaseTypeByssid(RResult result, GetBaseTypeListParam param) {

        BaseType baseType = new BaseType();
        baseType.setSsid(param.getSsid());
        BaseType selectOne = baseTypeMapper.selectOne(baseType);
        result.changeToTrue(selectOne);

        return result;
    }

    public RResult addBaseType(RResult result, GetBaseTypeListParam param) {

        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        ew.eq("typename", param.getTypename().trim());

        List<BaseType> baseTypes = baseTypeMapper.selectList(ew);
        if (baseTypes.size() > 0) {
            LogUtil.intoLog(1, this.getClass(), "该类型名称已经存在！");
            result.setMessage("该类型名称已经存在！");
            return result;
        }

        BaseType baseType = new BaseType();
        baseType.setTypename(param.getTypename());
        baseType.setSsid(OpenUtil.getUUID_32());


        boolean insert = baseType.insert();
        if (insert) {
            LogUtil.intoLog(1, this.getClass(), "成功添加一条授权类型：" + baseType.toString());
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(insert);

        return result;
    }

    public RResult updateBaseType(RResult result, GetBaseTypeListParam param) {

        EntityWrapper<BaseType> eww = new EntityWrapper<>();
        eww.eq("typename", param.getTypename().trim())
                .and()
                .ne("ssid", param.getSsid());

        List<BaseType> typeList = baseTypeMapper.selectList(eww);
        if (typeList.size() > 0) {
            LogUtil.intoLog(1, this.getClass(), "该类型名称已经存在！");
            result.setMessage("该类型名称已经存在！");
            return result;
        }

        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        BaseType baseType = new BaseType();
        baseType.setTypename(param.getTypename().trim());
        baseType.setOrdernum(param.getOrdernum());

        boolean update = baseType.update(ew);
        if (update) {
            LogUtil.intoLog(1, this.getClass(), "成功修改一条授权类型：" + baseType.toString());
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(update);

        return result;
    }

    public RResult deleteBaseTypeByssid(RResult result, GetBaseTypeListParam param) {

        EntityWrapper<BaseType> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        Integer delete = baseTypeMapper.delete(ew);

        LogUtil.intoLog(1, this.getClass(), "删除一条授权功能类型：bool=" + delete + "  ssid=" + param.getSsid());

        result.changeToTrue(delete);
        return result;
    }
}
