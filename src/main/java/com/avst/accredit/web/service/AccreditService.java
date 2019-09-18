package com.avst.accredit.web.service;

import com.avst.accredit.common.utils.DateUtil;
import com.avst.accredit.common.utils.INI4j;
import com.avst.accredit.common.utils.OpenUtil;
import com.avst.accredit.common.utils.RResult;
import com.avst.accredit.common.utils.properties.PropertiesListenerConfig;
import com.avst.accredit.common.utils.sq.CreateSQ;
import com.avst.accredit.common.utils.sq.NetTool;
import com.avst.accredit.common.utils.sq.SQEntity;
import com.avst.accredit.web.req.GetAccreditParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AccreditService {

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

    public void addAccredit(RResult result, GetAccreditParam param) {

        if(StringUtils.isEmpty(param.getClientName())){
            result.setMessage("单位名称不能为空");
            return ;
        }
        if(StringUtils.isEmpty(param.getUnitCode())){
            result.setMessage("单位简称不能为空");
            return ;
        }
        if(StringUtils.isEmpty(param.getServerType())){
            result.setMessage("授权服务类型不能为空");
            return ;
        }
        if(StringUtils.isEmpty(param.getGnlist())){
            result.setMessage("授权功能列表不能为空");
            return ;
        }
        if(StringUtils.isEmpty(param.getCpuCode())){
            result.setMessage("授权码不能为空");
            return ;
        }

        SQEntity sqEntity= new SQEntity();
        //授权的UnitCode一定是有规则的，例如：最上面的服务器是hb,下一级hb_wh,hb_wh_hk,最下级的客户端服务器也是hb_wh_hk；
        // 当前的节点服务器和该节点的下级服务器（客户端服务器）UnitCode一致，只是SortNum不同，节点是0，其他自动在上一个数值上加1
        sqEntity.setUnitCode(param.getUnitCode());
        sqEntity.setSqDay(param.getSqDay());
        sqEntity.setSortNum(param.getSortNum());
        sqEntity.setServerType(param.getServerType());
        sqEntity.setForeverBool(param.getForeverBool());//是否永久授权
        sqEntity.setClientName(param.getClientName());
        sqEntity.setCpuCode(param.getCpuCode());

        sqEntity.setGnlist(param.getGnlist()); //通过集合转成字符串，以|的方式分割
        sqEntity.setStartTime(DateUtil.getDateAndMinute());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String startTime = df.format(new Date());

        String path = OpenUtil.getXMSoursePath() + "\\shouquan\\" + startTime;

        System.out.println(path);
        boolean b = CreateSQ.deSQ(sqEntity, path);

        if (b) {
            //把授权信息保存到xml文件中，保存到缓存中
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
            INI4j ini4j = new INI4j(new File(path));

            map = ini4j.get();

            hashMap.put("serverType", map.get("serverType"));
            map.remove("serverType");
            hashMap.put("shouquan", map);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        result.changeToTrue(hashMap);
    }


}
