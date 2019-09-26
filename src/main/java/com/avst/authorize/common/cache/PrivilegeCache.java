package com.avst.authorize.common.cache;

import java.util.*;

public class PrivilegeCache {

    private static HashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> privilegeList = new LinkedHashMap();

    /**
     * 获取所有笔录状态缓存
     * @return
     */
    public static synchronized HashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> getPrivilegeList() {
        return privilegeList;
    }

    /**
     * 设置授权集合缓存
     * @param privilegeList
     */
    public static synchronized void setPrivilegeCacheList(HashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> privilegeList) {
        privilegeList = null;
        privilegeList = privilegeList;
    }

    /**
     * 设置授权缓存
     *
     * @param map
     */
    public static synchronized void setPrivilegeCache(String name, LinkedHashMap<String, LinkedHashMap<String, String>> map) {
        if (null == privilegeList || privilegeList.size() == 0) {
            privilegeList = new LinkedHashMap();
        }
        privilegeList.put(name, map);
    }

    /**
     * 移除某个
     * @param cpuCode
     */
    public static synchronized void removePrivilegeCacheById(String cpuCode){
        if (null == privilegeList || privilegeList.size() == 0) {
            return;
        }
        privilegeList.remove(cpuCode);
    }

    /**
     * 清空所有
     */
    public static synchronized void delPrivilegeCacheList(){
        privilegeList = null;
    }




}
