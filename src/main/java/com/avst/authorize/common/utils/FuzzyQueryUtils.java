package com.avst.authorize.common.utils;

import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.req.GetAuthorizeListParam;
import org.apache.commons.lang3.StringUtils;

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
    public static List<SQEntity> getPage (GetAuthorizeListParam param, List<SQEntity> list){
        List<SQEntity> fuzzyQuery = new ArrayList();

        Integer currPage = param.getCurrPage();
        Integer pageSize = param.getPageSize();

        if(currPage == 1){
            currPage--;
        }else if (currPage >= 2) {
            currPage = (currPage - 1) * pageSize;
        }

        int j = 1;
        for (int i = 0; i < list.size(); i++) {
            if (i >= currPage && j <= pageSize){
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
            if(matcher.find() ){
                fuzzyQuery.add(list.get(i));
            }
        }
        return fuzzyQuery;
    }

    /**
     * 模糊分页查询
     * @param param
     * @param list
     * @return
     */
    public static List<SQEntity> fuzzyQueryPage (GetAuthorizeListParam param, List<SQEntity> list){
        List<SQEntity> fuzzyQuery = new ArrayList();
        //大小写不敏感的时候，多加一个条件
        Pattern pattern = Pattern.compile(param.getClientName(), Pattern.CASE_INSENSITIVE);

        Integer currPage = param.getCurrPage();
        Integer pageSize = param.getPageSize();

        if(currPage == 1){
            currPage--;
        }else if (currPage >= 2) {
            currPage = (currPage - 1) * pageSize;
        }

        int j = 1;

        for(int i=0; i < list.size(); i++){
            Matcher matcher = pattern.matcher((list.get(i)).getClientName().toString());
            if(i >= currPage && j <= pageSize && matcher.find()){
                fuzzyQuery.add(list.get(i));
                j++;
            }
        }
        return fuzzyQuery;
    }


    /**
     * 条件查询获取总数
     * @param name
     * @param list
     * @return
     */
    public static Integer fuzzyQueryCount (String name, List<SQEntity> list){

        if(StringUtils.isEmpty(name)){
            return list.size();
        }

        List<SQEntity> fuzzyQuery = new ArrayList();
        //大小写不敏感的时候，多加一个条件
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        for(int i=0; i < list.size(); i++){
            Matcher matcher = pattern.matcher((list.get(i)).getClientName().toString());
            if(matcher.find()){
                fuzzyQuery.add(list.get(i));
            }
        }
        return fuzzyQuery.size();
    }
}