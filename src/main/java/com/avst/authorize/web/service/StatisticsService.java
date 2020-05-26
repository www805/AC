package com.avst.authorize.web.service;

import com.avst.authorize.common.entity.*;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.mapper.*;
import com.avst.authorize.web.vo.GetServerBaseStatisticsVO;
import com.avst.authorize.web.vo.GetYearStatisticsVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhuang
 * @Date: 2020/5/20 0020 09:58
 * @Description:
 */
@Service
public class StatisticsService {

    @Autowired
    private BaseGnInfoMapper baseGnInfoMapper;

    @Autowired
    private BaseGnTypeMapper baseGnTypeMapper;

    @Autowired
    private SQEntityMapper sqEntityMapper;

    @Autowired
    private SQCodeMapper sqCodeMapper;

    @Cacheable(cacheNames = "empserverbase", key = "1")
    public RResult getServerBaseStatistics(RResult result) {

        GetServerBaseStatisticsVO vo = new GetServerBaseStatisticsVO();

        //获取分支数量
        EntityWrapper<BaseGninfo> ewfz = new EntityWrapper<>();
        ewfz.eq("t.typecode", "fen");
        Integer branchCount = baseGnInfoMapper.getServerBaseStatistics(ewfz);
        //获取OEM版本数量
        EntityWrapper<BaseGninfo> ewoem = new EntityWrapper<>();
        ewoem.eq("t.typecode", "oem");
        Integer oemCount = baseGnInfoMapper.getServerBaseStatistics(ewoem);
        //获取授权功能数量
        EntityWrapper<BaseGninfo> ewgn = new EntityWrapper<>();
        ewgn.eq("t.typecode", "gn");
        Integer gnCount = baseGnInfoMapper.getServerBaseStatistics(ewgn);
        //获取全部授权数量
        EntityWrapper<SQEntityPlus> ewsqcount = new EntityWrapper<>();
        ewsqcount.eq("state", 1);
        Integer sqCount = sqEntityMapper.getSQCount(ewsqcount);

        vo.setBranchCount(branchCount);
        vo.setOemCount(oemCount);
        vo.setSqgnCount(gnCount);
        vo.setSqAllCount(sqCount);

        result.changeToTrue(vo);

        return result;
    }

    @Cacheable(cacheNames = "emppaihb", key = "1")
    public RResult getUsernamePaihb(RResult result) {
        EntityWrapper<SQEntity> ew = new EntityWrapper<>();
        List<SQCodeStatisticsEntity> SQCodeStatisticsEntityList = sqEntityMapper.getUserSQCodeCount(ew);
        result.changeToTrue(SQCodeStatisticsEntityList);
        return result;
    }

    @Cacheable(cacheNames = "empyear", key = "1")
    public RResult getYearStatistics(RResult result) {

        //最近一年的授权统计
        EntityWrapper<YearEntity> ew = new EntityWrapper<>();
        ew.eq("s.state", 1);
        List<YearEntity> yearStatistics = sqCodeMapper.getYearStatistics(ew);

        //获取过去12个月时间
        List<YearEntity> yearEntities = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for(long i = 11L;i >= 0L; i--){
            LocalDate localDate = today.minusMonths(i);
            String year = localDate.toString().substring(0,7);
            YearEntity yearEntity = new YearEntity();
            for (int j = 0; j < yearStatistics.size(); j++) {
                YearEntity sqlYear = yearStatistics.get(j);
                yearEntity.setYear(year);
                if(StringUtils.isNotEmpty(sqlYear.getYear()) && year.equals(sqlYear.getYear())){
                    yearEntity.setSqcount(sqlYear.getSqcount());
                    break;
                }else{
                    yearEntity.setSqcount(0);
                }
            }
            yearEntities.add(yearEntity);
        }

        result.changeToTrue(yearEntities);
        return result;
    }

    @Cacheable(cacheNames = "empeles", key = "1")
    public RResult getElesStatistics(RResult result) {

        GetYearStatisticsVO vo = new GetYearStatisticsVO();

        //申请人统计
        EntityWrapper<SQEntity> ew = new EntityWrapper<>();
        List<SQCodeStatisticsEntity> userSQCodeCount = sqEntityMapper.getUserSQCodeCount(ew);

        //公司授权统计
        EntityWrapper<SQEntity> ewcom = new EntityWrapper<>();
        List<SQCodeStatisticsEntity> companySQCodeCount = sqEntityMapper.getCompanynameSQCodeCount(ewcom);

        //类型授权统计
        EntityWrapper<SQEntity> ewtype = new EntityWrapper<>();
        List<SQCodeStatisticsEntity> typeSQCodeCount = sqEntityMapper.getTypenameSQCodeCount(ewtype);

        //客户端授权统计
        List<BaseGnType> baseGnTypes = baseGnTypeMapper.getBaseNgType();

        EntityWrapper<SQEntityPlus> ewep = new EntityWrapper<>();
        ewep.eq("s.state", 1);
        List<SQEntityPlus> sqEntityPlusList = sqEntityMapper.getSQList(ewep);

        //遍历所有分类
        for (BaseGnType baseGnType : baseGnTypes) {
            //取出所有类型
            List<BaseGninfo> baseGnInfos = baseGnType.getBaseGninfo();
            //取出分类名字
            String typecode = baseGnType.getTypecode();
            //分类数组
            List<SQCodeStatisticsEntity> sqCodeCountList = new ArrayList<>();

            //遍历类型
            for (BaseGninfo baseGnInfo : baseGnInfos) {
                //创建存放容器
                SQCodeStatisticsEntity sqCodeStatisticsEntity = new SQCodeStatisticsEntity();
                //取出功能名字
                String title = baseGnInfo.getTitle();
                sqCodeStatisticsEntity.setName(title);
                //取出功能code
                String gnname = baseGnInfo.getName();

                //遍历所有授权
                for (SQEntityPlus sqEntityPlus : sqEntityPlusList) {
                    //获取授权功能
                    String gnlist = sqEntityPlus.getGnlist();
                    List<SQCode> sqCodeList = sqEntityPlus.getSqCodeList();
                    //拆分功能
                    String[] split = gnlist.split("\\|");

                    for (String sp : split) {
                        //授权功能和功能code对比
                        if(StringUtils.isNotEmpty(gnname) && sp.equals(gnname)){
                            //匹配正确放到容器里面，容器自增+授权台数
                            Integer value = sqCodeStatisticsEntity.getValue();
                            Integer changdu = 1;
                            if(null != sqCodeList && sqCodeList.size() > 0){
                                changdu = sqCodeList.size();
                            }
                            sqCodeStatisticsEntity.setValue(value + changdu);
                            break;
                        }
                    }
                }
                //添加到数组里面
                sqCodeCountList.add(sqCodeStatisticsEntity);
            }

            //分支版本授权统计
            if("fen".equals(typecode)){
                vo.setBranchSQCodeCount(sqCodeCountList);
            }
            //OEM版本授权统计
            if("oem".equals(typecode)){
                vo.setOemSQCodeCount(sqCodeCountList);
            }
            //客户端
            if("duan".equals(typecode)){
                vo.setClientSQCodeCount(sqCodeCountList);
            }

        }

        vo.setUserSQCodeCount(userSQCodeCount);
        vo.setCompanySQCodeCount(companySQCodeCount);
        vo.setTypeSQCodeCount(typeSQCodeCount);

        result.changeToTrue(vo);
        return result;
    }
}
