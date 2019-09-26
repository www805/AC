package com.avst.authorize.web.service;

import com.avst.authorize.common.cache.PrivilegeCache;
import com.avst.authorize.common.cache.SqCache;
import com.avst.authorize.common.utils.FuzzyQueryUtils;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.common.utils.properties.PropertiesListenerConfig;
import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.dao.SQEntityRoom_R_W_XML;
import com.avst.authorize.web.req.GetAuthorizeListParam;
import com.avst.authorize.web.vo.GetAuthorizeListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MainService {

    @Autowired
    private AuthorizeService authorizeService;

    public RResult getAuthorizeList(RResult result, GetAuthorizeListParam param) {

        GetAuthorizeListVO authorizeListVO = new GetAuthorizeListVO();

        //读取所有xml数据，分页
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();

        //时间排序
        ListSort(sqCacheList);

        if (null == sqCacheList || sqCacheList.size() == 0) {
            //读取xml文件
            getSqXmltoCache();
            sqCacheList = SqCache.getSqCacheList();
        }

        //如果没有就把gn集合放进缓存里
        List<String> gnList = SqCache.getSqGnList();
        if (null == gnList || gnList.size() == 0) {
            ArrayList<String> gns = new ArrayList<>();
            for (SQEntity entity : sqCacheList) {
                gns.add(entity.getGnlist());
            }
            SqCache.setSqGnList(gns);
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



        authorizeListVO.setPagelist(list);
        authorizeListVO.setPageparam(param);

        result.changeToTrue(authorizeListVO);
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
        List<String> gnList = SqCache.getSqGnList();

        List<SQEntity> sqEntityList = SqCache.getSqCacheList();

        if (null != gnList && gnList.size() > 0 && null != sqEntityList && sqEntityList.size() > 0) {

            for (int i = 0; i < sqEntityList.size(); i++) {
                SQEntity entity = sqEntityList.get(i);
                entity.setGnlist(gnList.get(i));
            }

            SqCache.setSqCacheList(sqEntityList);
            SqCache.setSqGnList(null);//清空功能缓存
        }

        //放入缓存
        SqCache.setSqCacheByEntity(sqEntity);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

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
            authorizeService.getPrivilege(result);
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
