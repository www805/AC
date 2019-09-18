package com.avst.accredit.common.cache;

import com.avst.accredit.common.utils.sq.SQEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqCache {

    private static List<SQEntity> SqCacheList = new ArrayList();

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
//            for (SQEntity sq : SqCacheList) {
//                if (sq.getCpuCode().equals(sqEntity.getCpuCode())) {
//                    cunzai = true;
//                    sq.setClientName(sqEntity.getClientName());
//                    sq.setUnitCode(sqEntity.getUnitCode());
//                    sq.setSqDay(sqEntity.getSqDay());
//                    sq.setSortNum(sqEntity.getSortNum());
//                    sq.setServerType(sqEntity.getServerType());
//                    sq.setForeverBool(sqEntity.isForeverBool());
//                    sq.setGnlist(sqEntity.getGnlist());
//                    sq.setStartTime(sqEntity.getStartTime());
//                    break;
//                }else{
//                    cunzai = false;
//                }
//            }
            if(cunzai == false){
                SqCacheList.add(sqEntity);
            }
        }
    }

    /**
     * 移除某个
     * @param cpuCode
     */
    public static synchronized void removeSqCacheByCpuCode(String cpuCode){

        if(null==SqCacheList||SqCacheList.size() == 0){
            return ;
        }
        Iterator<SQEntity> iterator = SqCacheList.iterator();
        while (iterator.hasNext()) {
            SQEntity param = iterator.next();
            if (param.getCpuCode().equals(cpuCode)) {
                iterator.remove();
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


}
