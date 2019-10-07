package com.avst.authorize.common.cache;

import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.utils.sq.SQEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqCache {

    private static List<SQEntityPlus> SqCacheList;
    private static List<String> SqGnList;

    /**
     * 获取所有笔录状态缓存
     * @return
     */
    public static synchronized List<SQEntityPlus> getSqCacheList() {
        if(null==SqCacheList||SqCacheList.size() == 0) {
            SqCacheList = new ArrayList<SQEntityPlus>();
        }
        return SqCacheList;
    }

    /**
     * 设置授权集合缓存
     * @param sqEntitys
     */
    public static synchronized void setSqCacheList(List<SQEntityPlus> sqEntitys) {
        SqCacheList = null;
        SqCacheList = sqEntitys;
    }

    /**
     * 设置授权缓存
     * @param sqEntity
     */
    public static synchronized void setSqCacheByEntity(SQEntityPlus sqEntity) {
        if(null==SqCacheList || SqCacheList.size() == 0){
            SqCacheList=new ArrayList<SQEntityPlus>();
            SqCacheList.add(sqEntity);
        }else{
            boolean cunzai = false;
            for (SQEntityPlus sq : SqCacheList) {
                if (sq.getSsid().equals(sqEntity.getSsid())) {
                    cunzai = true;
                    sq.setClientName(sqEntity.getClientName());
                    sq.setUnitCode(sqEntity.getUnitCode());
                    sq.setSqDay(sqEntity.getSqDay());
                    sq.setSortNum(sqEntity.getSortNum());
                    sq.setCpuCode(sqEntity.getCpuCode());
                    sq.setServerType(sqEntity.getServerType());
                    sq.setForeverBool(sqEntity.isForeverBool());
                    sq.setGnlist(sqEntity.getGnlist());
                    sq.setStartTime(sqEntity.getStartTime());
                    break;
                }else{
                    cunzai = false;
                }
            }
            if(cunzai == false){
                SqCacheList.add(sqEntity);
            }
        }
    }

    /**
     * 移除某个
     * @param ssid
     */
    public static synchronized void removeSqCacheBySsid(String ssid){

        if(null==SqCacheList||SqCacheList.size() == 0){
            return ;
        }
        Iterator<SQEntityPlus> iterator = SqCacheList.iterator();
        while (iterator.hasNext()) {
            SQEntityPlus param = iterator.next();
            if (param.getSsid().equals(ssid)) {
                param.setState("0");
//                iterator.remove();
                break;
            }
        }
    }

    /**
     * 清空所有
     */
    public static synchronized void delSqCacheList(){
        SqCache.SqCacheList = null;
    }


    /**
     * 设置授权list（增、删的时候使用）
     * @param sqGnList
     */
    public static synchronized void setSqGnList(List<String> sqGnList) {
        SqCache.SqGnList = sqGnList;
    }

    /**
     * 获取授权的list
     */
    public static synchronized List<String> getSqGnList() {
        return SqCache.SqGnList;
    }

}
