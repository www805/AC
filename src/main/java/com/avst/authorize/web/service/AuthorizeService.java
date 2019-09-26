package com.avst.authorize.web.service;

import com.avst.authorize.common.cache.PrivilegeCache;
import com.avst.authorize.common.cache.SqCache;
import com.avst.authorize.common.utils.DateUtil;
import com.avst.authorize.common.utils.INI4j;
import com.avst.authorize.common.utils.OpenUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.common.utils.properties.PropertiesListenerConfig;
import com.avst.authorize.common.utils.sq.CreateSQ;
import com.avst.authorize.common.utils.sq.NetTool;
import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.dao.SQEntityRoom_R_W_XML;
import com.avst.authorize.web.req.GetAuthorizeParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthorizeService {

    @Autowired
    private MainService mainService;

    public void uploadBytxt(RResult result, MultipartFile file) {

        if (file.isEmpty()) {
            result.setMessage("上传失败，请选择文件");
            return;
        }

        InputStream inputStream = null;
        String cpuCode = "";
        try {
            inputStream = file.getInputStream();

            byte[] buf = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buf)) != -1){
                cpuCode += new String(buf, 0, length);
                System.out.print(cpuCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (StringUtils.isNotEmpty(cpuCode)) {
            result.changeToTrue(cpuCode.trim());
        }

    }

    public void addAuthorize(RResult result, GetAuthorizeParam param) {

        Integer sortNum = 0;

        //读出缓存判断，排序
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();
        ArrayList<Integer> sortNumList = new ArrayList<>();
        for (SQEntity sqEntity : sqCacheList) {

            if (param.getUnitCode().equals(sqEntity.getUnitCode())) {
                sortNumList.add(sqEntity.getSortNum());
            }

        }

        if (sortNumList.size() > 0) {
            Collections.sort(sortNumList);
            sortNum = sortNumList.get(sortNumList.size() - 1);
            sortNum++;
        }

        SQEntity sqEntity= new SQEntity();
        //授权的UnitCode一定是有规则的，例如：最上面的服务器是hb,下一级hb_wh,hb_wh_hk,最下级的客户端服务器也是hb_wh_hk；
        // 当前的节点服务器和该节点的下级服务器（客户端服务器）UnitCode一致，只是SortNum不同，节点是0，其他自动在上一个数值上加1
        sqEntity.setUnitCode(param.getUnitCode());
        sqEntity.setSqDay(param.getSqDay());
        sqEntity.setSortNum(sortNum);//排序
        sqEntity.setServerType(param.getServerType());
        sqEntity.setForeverBool(param.getForeverBool());//是否永久授权
        sqEntity.setClientName(param.getClientName());
        sqEntity.setCpuCode(param.getCpuCode());

        sqEntity.setGnlist(param.getGnlist()); //通过集合转成字符串，以|的方式分割
        sqEntity.setStartTime(DateUtil.getDateAndMinute());
        sqEntity.setSsid(OpenUtil.getUUID_32());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String startTime = df.format(new Date());

        String path = OpenUtil.getXMSoursePath() + "\\shouquan\\" + param.getClientName() + startTime;

        System.out.println(path);
        boolean b = CreateSQ.deSQ(sqEntity, path);

        if (b) {
            //把授权信息保存到xml文件中，保存到缓存中(为了提高处理速度，用了多线程)
            mainService.setSqInfo(sqEntity);
            result.changeToTrue(b);
            NetTool.executeCMD("explorer " + path);//打开文件夹
        }
    }

    public void getPrivilege(RResult result) {

        /**
         * 授权客户端的功能列表
         * 客户端的功能列表，暂时只有：record_f、asr_f、tts_f、fd_f、ph_f(笔录管理、语音识别、语音播报、设备控制、测谎仪)
         * 单机版/联机版（s_v、o_v）
         * 分支版本：公安、纪委、监察委（ga_t、jw_t、jcw_t）
         * OEM版本：通用、HK（common_o、hk_o）
         * @return
         */

        String filename= PropertiesListenerConfig.getProperty("file.ini.name");

        String path = OpenUtil.getXMSoursePath() + "\\" + filename;

        LinkedHashMap<String, LinkedHashMap<String, String>> map = null;
        HashMap hashMap = new HashMap();
        try {
            hashMap = PrivilegeCache.getPrivilegeList();

            if (null == hashMap || hashMap.size() == 0) {
                INI4j ini4j = new INI4j(new File(path));
                map = ini4j.get();

                hashMap.put("serverType", map.get("serverType"));
                map.remove("serverType");
                hashMap.put("shouquan", map);

                PrivilegeCache.setPrivilegeCacheList(hashMap);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        result.changeToTrue(hashMap);
    }

    public void delAuthorize(RResult result, GetAuthorizeParam param) {

        String ssid = param.getSsid();

        System.out.println(ssid);

        //获取xml保存路径
        String filename= PropertiesListenerConfig.getProperty("file.data.url");

        //xml保存的固定地址
        String page = OpenUtil.getXMSoursePath() + filename;

        //把json转换成集合
        List<String> gnList = SqCache.getSqGnList();
        List<SQEntity> sqCacheList = SqCache.getSqCacheList();
        if (null != gnList && gnList.size() > 0 && null != sqCacheList && sqCacheList.size() > 0) {

            for (int i = 0; i < sqCacheList.size(); i++) {
                SQEntity entity = sqCacheList.get(i);
                entity.setGnlist(gnList.get(i));
            }
            SqCache.setSqCacheList(sqCacheList);
            SqCache.setSqGnList(null);
        }

        SqCache.removeSqCacheBySsid(ssid);
        SQEntityRoom_R_W_XML.writeXml_courtRoomList(page, sqCacheList);

        SqCache.delSqCacheList();//清空缓存

        result.changeToTrue();
    }
}
