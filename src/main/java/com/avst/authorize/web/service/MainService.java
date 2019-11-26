package com.avst.authorize.web.service;

import com.avst.authorize.common.cache.SqCache;
import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.common.utils.properties.PropertiesListenerConfig;
import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.mapper.SQEntityMapper;
import com.avst.authorize.web.mapper.SQEntityRoom_R_W_XML;
import com.avst.authorize.web.req.GetAuthorizeListParam;
import com.avst.authorize.web.vo.GetAuthorizeListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MainService {

    /*以下方法可全部删除*/


    //新增xml
    public void setSqInfo(SQEntityPlus sqEntity) {

        //先获取缓存里是否为空，如果为空，从xml读取数据
        List<SQEntityPlus> sqCacheList = SqCache.getSqCacheList();
        if (null == sqCacheList || sqCacheList.size() == 0) {
            //读取xml文件
            getSqXmltoCache();
        }

        //获取xml保存路径
        String filename = PropertiesListenerConfig.getProperty("file.data.url");

        //xml保存的固定地址
        String page = OpenUtil.getXMSoursePath() + filename;

        //把json转换成集合
        List<String> gnList = SqCache.getSqGnList();

        List<SQEntityPlus> sqEntityList = SqCache.getSqCacheList();

        if (null != gnList && gnList.size() > 0 && null != sqEntityList && sqEntityList.size() > 0) {

            for (int i = 0; i < sqEntityList.size(); i++) {
                SQEntityPlus entity = sqEntityList.get(i);
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

        //如果外部xml文件读出来的没有数据，就把当前新增的添进去
        if (null != sqEntityList && sqEntityList.size() == 0) {
            sqEntityList.add(sqEntity);
        }

        SQEntityRoom_R_W_XML.writeXml_courtRoomList(page, sqEntityList);
//        SQEntityRoom_R_W_XML.writeXml_courtRoom(page, sqEntity);
    }

    //读取xml到缓存
    public void getSqXmltoCache() {

        //获取xml保存路径
        String filename = PropertiesListenerConfig.getProperty("file.data.url");

        //xml保存的固定地址
        String page = OpenUtil.getXMSoursePath() + filename;

        List<SQEntityPlus> sqEntitieList = SQEntityRoom_R_W_XML.readXml(page);
        SqCache.setSqCacheList(sqEntitieList);
    }


    //按时间排序
    private void ListSort(List<SQEntityPlus> list) {
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
