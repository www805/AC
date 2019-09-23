package com.avst.accredit.common.cache;

import com.avst.accredit.common.utils.sq.SQEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqCache {

    private static List<SQEntity> SqCacheList;
    private static String sqJson;

    /**
     * 获取所有笔录状态缓存
     * @return
     */
    public static synchronized List<SQEntity> getSqCacheList() {
        if(null==SqCacheList||SqCacheList.size() == 0) {
            SqCacheList = new ArrayList<SQEntity>();
        }
        return SqCacheList;
    }

    /**
     * 设置授权集合缓存
     * @param sqEntitys
     */
    public static synchronized void setSqCacheList(List<SQEntity> sqEntitys) {
        SqCacheList = null;
        SqCacheList = sqEntitys;
    }

    /**
     * 设置授权缓存
     * @param sqEntity
     */
    public static synchronized void setSqCacheByEntity(SQEntity sqEntity) {
        if(null==SqCacheList || SqCacheList.size() == 0){
            SqCacheList=new ArrayList<SQEntity>();
            SqCacheList.add(sqEntity);
        }else{
            boolean cunzai = false;
            for (SQEntity sq : SqCacheList) {
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
        Iterator<SQEntity> iterator = SqCacheList.iterator();
        while (iterator.hasNext()) {
            SQEntity param = iterator.next();
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
        SqCacheList = null;
    }


    /**
     * 设置授权json（增、删的时候使用）
     * @param sqjsonstr
     */
    public static synchronized void setSqJson(String sqjsonstr) {
        sqJson = sqjsonstr;
    }

    /**
     * 获取授权的json
     */
    public static synchronized String getSqJson() {
        return sqJson;
    }
}
