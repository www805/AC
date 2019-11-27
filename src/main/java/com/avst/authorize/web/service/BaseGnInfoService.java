package com.avst.authorize.web.service;


import com.avst.authorize.common.cache.BaseGnInfoCache;
import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.BaseType;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.mapper.BaseGnInfoMapper;
import com.avst.authorize.web.mapper.BaseTypeMapper;
import com.avst.authorize.web.req.GetBaseGnInfoListParam;
import com.avst.authorize.web.req.GetBaseTypeListParam;
import com.avst.authorize.web.vo.GetBaseGnInfoListVO;
import com.avst.authorize.web.vo.GetBaseTypeListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseGnInfoService {

    @Autowired
    private BaseGnInfoMapper baseGnInfoMapper;

    public RResult getBaseGnInfoList(RResult result, GetBaseGnInfoListParam param) {

        GetBaseGnInfoListVO baseGnInfoListVO = new GetBaseGnInfoListVO();

        EntityWrapper<BaseGninfo> ew = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(param.getTitle())){
            ew.like("title", param.getTitle());
        }

        ew.orderBy("id", false);

        Integer count = baseGnInfoMapper.selectCount(ew);
        param.setRecordCount(count);

        Page<BaseGninfo> page = new Page<>(param.getCurrPage(), param.getPageSize());
        List<BaseGninfo> sqCacheList = baseGnInfoMapper.getBaseGnInfoList(page, ew);


        baseGnInfoListVO.setPagelist(sqCacheList);
        baseGnInfoListVO.setPageparam(param);

        result.changeToTrue(baseGnInfoListVO);
        return result;
    }

    public RResult getBaseGnInfoByssid(RResult result, GetBaseGnInfoListParam param) {

        BaseGninfo baseGninfo = new BaseGninfo();
        baseGninfo.setSsid(param.getSsid());

        baseGninfo = baseGnInfoMapper.selectOne(baseGninfo);

        result.changeToTrue(baseGninfo);
        return result;
    }

    public RResult deleteBaseGnInfoByssid(RResult result, GetBaseGnInfoListParam param) {

        EntityWrapper<BaseGninfo> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        Integer delete = baseGnInfoMapper.delete(ew);
        LogUtil.intoLog(1, this.getClass(), "删除一条授权功能：bool=" + delete + "  ssid=" + param.getSsid());

        result.changeToTrue(delete);
        return result;
    }

    public RResult addBaseGnInfo(RResult result, GetBaseGnInfoListParam param) {

        EntityWrapper<BaseGninfo> ew = new EntityWrapper<>();
        ew.eq("title", param.getTitle().trim())
                .or()
                .eq("name", param.getName().trim());

        List<BaseGninfo> baseGninfos = baseGnInfoMapper.selectList(ew);
        if (baseGninfos.size() > 0) {
            result.setMessage("该功能标题或授权代号已经存在！");
            return result;
        }

        BaseGninfo baseGninfo = new BaseGninfo();
        baseGninfo.setName(param.getName().trim());
        baseGninfo.setTitle(param.getTitle().trim());
        baseGninfo.setBtypessid(param.getBtypessid());
        baseGninfo.setSsid(OpenUtil.getUUID_32());

        boolean insert = baseGninfo.insert();
        if (insert) {
            LogUtil.intoLog(1, this.getClass(), "成功添加一条授权功能：" + baseGninfo.toString());
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(insert);

        return result;
    }

    public RResult updateBaseGnInfo(RResult result, GetBaseGnInfoListParam param) {

        EntityWrapper<BaseGninfo> eww = new EntityWrapper<>();
        eww.eq("name", param.getName().trim())
                .and()
                .ne("ssid",param.getSsid())
                .or()
                .eq("title",param.getTitle().trim())
                .and()
                .ne("ssid",param.getSsid());

        List<BaseGninfo> gninfos = baseGnInfoMapper.selectList(eww);
        if (gninfos.size() > 0) {
            result.setMessage("该功能标题或授权代号已经存在！");
            return result;
        }

        EntityWrapper<BaseGninfo> ew = new EntityWrapper<>();
        ew.eq("ssid", param.getSsid());

        BaseGninfo baseGninfo = new BaseGninfo();
        baseGninfo.setName(param.getName().trim());
        baseGninfo.setTitle(param.getTitle().trim());
        baseGninfo.setBtypessid(param.getBtypessid());
        baseGninfo.setSsid(param.getSsid());

        boolean update = baseGninfo.update(ew);
        if (update) {
            LogUtil.intoLog(1, this.getClass(), "成功修改一条授权功能：" + baseGninfo.toString());
            BaseGnInfoCache.delBaseGninfoListCache();
        }
        result.changeToTrue(update);

        return result;
    }
}
