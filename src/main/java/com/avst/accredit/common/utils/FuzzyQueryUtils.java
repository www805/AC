package com.avst.accredit.common.utils;

import com.avst.accredit.common.utils.sq.SQEntity;
import com.avst.accredit.web.req.GetAccreditListParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuzzyQueryUtils {

    /**
     * 分页
     * @param param
     * @param list
     * @return
     */
    public static List<SQEntity> getPage (GetAccreditListParam param, List<SQEntity> list){
        List<SQEntity> fuzzyQuery = new ArrayList();

        Integer currPage = param.getCurrPage();
        Integer pageSize = param.getPageSize();

        if (currPage == 1) {
            currPage = 0;
        } else if (currPage == 2) {

        } else if (currPage >= 3) {
            currPage = (currPage * pageSize) - 1;
        }

//        currPage = (currPage * pageSize) - 1;

//        if (currPage < 1) {
//            currPage = 0;
//        } else if (currPage > 1) {
////            currPage--;
//
//        }

        int j = 1;

        for (int i = 0; i < list.size(); i++) {
            if (i >= currPage && j <= pageSize) {
                fuzzyQuery.add(list.get(i));
                j++;
            }
        }
        return fuzzyQuery;
    }

    /**
     * 模糊查询
     * @param name
     * @param list
     * @return
     */
    public static List<SQEntity> fuzzyQuery (String name, List<SQEntity> list){
        List<SQEntity> fuzzyQuery = new ArrayList();
        //大小写不敏感的时候，多加一个条件
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        for(int i=0; i < list.size(); i++){
            Matcher matcher = pattern.matcher((list.get(i)).getClientName().toString());
            if(matcher.find()){
                fuzzyQuery.add(list.get(i));
            }
        }
        return fuzzyQuery;
    }
}