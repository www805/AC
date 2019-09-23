package com.avst.accredit.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.avst.accredit.common.cache.PrivilegeCache;
import com.avst.accredit.common.cache.SqCache;
import com.avst.accredit.common.utils.FuzzyQueryUtils;
import com.avst.accredit.common.utils.OpenUtil;
import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.common.utils.properties.PropertiesListenerConfig;
import com.avst.accredit.common.utils.sq.SQEntity;
import com.avst.accredit.web.dao.SQEntityRoom_R_W_XML;
import com.avst.accredit.web.req.GetAccreditListParam;
import com.avst.accredit.web.vo.GetAccreditListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MainService {

    @Autowired
    private AccreditService accreditService;

    public RResult getAccreditList(RResult result, GetAccreditListParam param) {

        GetAccreditListVO accreditListVO = new GetAccreditListVO();

        //读取所有xml数据，分页
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();

        //时间排序
        ListSort(sqCacheList);

        if (null == sqCacheList || sqCacheList.size() == 0) {
            //读取xml文件
            getSqXmltoCache();
            sqCacheList = SqCache.getSqCacheList();
        }

        //如果没有就把对象转成json
        String sqJson = SqCache.getSqJson();
        if(StringUtils.isEmpty(sqJson)){
            sqJson = JSON.toJSONString(sqCacheList);
            SqCache.setSqJson(sqJson);
        }

        for (SQEntity sqEntity : sqCacheList) {
            String gn = sqEntity.getGnlist();
            //判断是哪个就替换成中文
            gn = getZW(gn);
            sqEntity.setGnlist(gn);
        }


        //做好分页
        Integer unum = FuzzyQueryUtils.fuzzyQueryCount(param.getClientName(), sqCacheList);
        param.setRecordCount(unum);

        if (null == unum || unum < 1) {
            param.setPageCount(0);
        } else {
            if (unum % param.getPageSize() == 0) {
                param.setPageCount(unum / param.getPageSize());
            } else {
                param.setPageCount(unum / param.getPageSize() + 1);
            }
        }

        List<SQEntity> list = null;

        //判断是否有条件
        if (StringUtils.isNotEmpty(param.getClientName())) {
            list = FuzzyQueryUtils.fuzzyQueryPage(param, sqCacheList);
        } else {
            list = FuzzyQueryUtils.getPage(param, sqCacheList);
        }



        accreditListVO.setPagelist(list);
        accreditListVO.setPageparam(param);

        result.changeToTrue(accreditListVO);
        return result;
    }

    //新增xml
    public void setSqInfo(SQEntity sqEntity){

        //先获取缓存里是否为空，如果为空，从xml读取数据
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();
        if (null == sqCacheList || sqCacheList.size() == 0) {
            //读取xml文件
            getSqXmltoCache();
        }

        //获取xml保存路径
        String filename= PropertiesListenerConfig.getProperty("file.data.url");

        //xml保存的固定地址
        String page = OpenUtil.getXMSoursePath() + filename;

        //把json转换成集合
        String sqJson = SqCache.getSqJson();
        if(StringUtils.isNotEmpty(sqJson)){
            List<SQEntity> sqEntities = JSON.parseObject(sqJson, new TypeReference<List<SQEntity>>() {});
            SqCache.setSqCacheList(sqEntities);
            SqCache.setSqJson("");
        }

        //放入缓存
        SqCache.setSqCacheByEntity(sqEntity);
        List<SQEntity> sqEntityList = SqCache.getSqCacheList();
        SQEntityRoom_R_W_XML.writeXml_courtRoomList(page, sqEntityList);
//        SQEntityRoom_R_W_XML.writeXml_courtRoom(page, sqEntity);
    }

    //读取xml到缓存
    public void getSqXmltoCache(){

        //获取xml保存路径
        String filename= PropertiesListenerConfig.getProperty("file.data.url");

        //xml保存的固定地址
        String page = OpenUtil.getXMSoursePath() + filename;

        List<SQEntity> sqEntitieList = SQEntityRoom_R_W_XML.readXml(page);
        SqCache.setSqCacheList(sqEntitieList);
    }

    //转换成中文
    public String getZW(String str){
        HashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> map = PrivilegeCache.getPrivilegeList();

        if (null == map || map.size() == 0) {
            RResult result = new RResult();
            accreditService.getPrivilege(result);
            map = (HashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>) result.getData();
        }

//        System.out.println(map);


        LinkedHashMap<String, LinkedHashMap<String, String>> shouquan = map.get("shouquan");

//        System.out.println(shouquan);

        Set<String> keys = shouquan.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {

            String key = iterator.next();
            LinkedHashMap<String, String> hashMap = shouquan.get(key);

            Set<String> keySet = hashMap.keySet();
            Iterator<String> stringIterator = keySet.iterator();
            while (stringIterator.hasNext()) {
                String next = stringIterator.next();
                String value = hashMap.get(next);
                str = str.replace(next, value);
            }
        }

        return str;
    }



    //按时间排序
    private void ListSort(List<SQEntity> list) {
        Collections.sort(list, new Comparator<SQEntity>() {
            @Override
            //定义一个比较器
            public int compare(SQEntity o1, SQEntity o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getStartTime());
                    Date dt2 = format.parse(o2.getStartTime());
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }


}
