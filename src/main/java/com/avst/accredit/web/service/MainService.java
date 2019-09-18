package com.avst.accredit.web.service;

import com.avst.accredit.common.cache.SqCache;
import com.avst.accredit.common.utils.FuzzyQueryUtils;
import com.avst.accredit.common.utils.OpenUtil;
import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.common.utils.sq.SQEntity;
import com.avst.accredit.web.dao.SQEntityRoom_R_W_XML;
import com.avst.accredit.web.req.GetAccreditListParam;
import com.avst.accredit.web.vo.GetAccreditListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    private String page = OpenUtil.getXMSoursePath() + "\\data\\data.xml";

    public void getAccreditList(RResult result, GetAccreditListParam param) {

        GetAccreditListVO accreditListVO = new GetAccreditListVO();

        //读取所有xml数据，分页
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();
        if (null == sqCacheList || sqCacheList.size() == 0) {
            //读取xml文件
            getSqXmltoCache();
            sqCacheList = SqCache.getSqCacheList();
        }

//        for (SQEntity sqEntity : sqCacheList) {
//            String gn = sqEntity.getGnlist();
//
//            String[] splist = gn.split("|");
//        }

        //判断是哪个就替换成中文


        //做好分页
        Integer unum = sqCacheList.size();
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
            list = FuzzyQueryUtils.fuzzyQuery(param.getClientName(), sqCacheList);
        } else {
            list = FuzzyQueryUtils.getPage(param, sqCacheList);
        }

        accreditListVO.setPagelist(list);
        accreditListVO.setPageparam(param);

        result.changeToTrue(accreditListVO);

    }


    //新增xml
    public void setSqInfo(SQEntity sqEntity){

        //先获取缓存里是否为空，如果为空，从xml读取数据
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();
        if (null == sqCacheList || sqCacheList.size() == 0) {
            //读取xml文件
            getSqXmltoCache();
        }

        //放入缓存
        SqCache.setSqCacheByEntity(sqEntity);
        List<SQEntity> sqEntityList = SqCache.getSqCacheList();
        SQEntityRoom_R_W_XML.writeXml_courtRoomList(page, sqEntityList);
    }

    //读取xml到缓存
    public void getSqXmltoCache(){
        List<SQEntity> sqEntitieList = SQEntityRoom_R_W_XML.readXml(page);
        SqCache.setSqCacheList(sqEntitieList);
    }

}
