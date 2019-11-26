package com.avst.authorize.common.cache;

import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.utils.SpringUtil;
import com.avst.authorize.web.mapper.BaseGnInfoMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseGnInfoCache {

    private static List<BaseGninfo> BaseGninfoListCache;

    /**
     * 获取所有笔录状态缓存
     * @return
     */
    public static synchronized List<BaseGninfo> getBaseGninfoListCache() {
        if(null==BaseGninfoListCache||BaseGninfoListCache.size() == 0) {
            BaseGnInfoMapper baseGnInfoMapper = SpringUtil.getBean(BaseGnInfoMapper.class);
            BaseGninfoListCache = baseGnInfoMapper.selectList(null);
        }
        return BaseGninfoListCache;
    }

    /**
     * 移除某个
     * @param ssid
     */
    public static synchronized void removeBaseGninfoListCache(String ssid){

        if(null==BaseGninfoListCache||BaseGninfoListCache.size() == 0){
            return ;
        }
        Iterator<BaseGninfo> iterator = BaseGninfoListCache.iterator();
        while (iterator.hasNext()) {
            BaseGninfo param = iterator.next();
            if (param.getSsid().equals(ssid)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * 清空所有
     */
    public static synchronized void delBaseGninfoListCache(){
        BaseGnInfoCache.BaseGninfoListCache = null;
    }



}
