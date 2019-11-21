package com.avst.authorize.web.service;

import com.avst.authorize.common.cache.SqCache;
import com.avst.authorize.common.entity.BaseGninfo;
import com.avst.authorize.common.entity.BaseType;
import com.avst.authorize.common.entity.SQCode;
import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.utils.*;
import com.avst.authorize.common.utils.properties.PropertiesListenerConfig;
import com.avst.authorize.common.utils.sq.CreateSQ;
import com.avst.authorize.web.mapper.BaseGnInfoMapper;
import com.avst.authorize.web.mapper.BaseTypeMapper;
import com.avst.authorize.web.mapper.SQCodeMapper;
import com.avst.authorize.web.mapper.SQEntityMapper;
import com.avst.authorize.web.req.GetAuthorizeParam;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthorizeService {

    @Autowired
    private MainService mainService;

    @Autowired
    private BaseTypeMapper baseTypeMapper;

    @Autowired
    private BaseGnInfoMapper baseGnInfoMapper;

    @Autowired
    private SQCodeMapper sqCodeMapper;

    @Autowired
    private SQEntityMapper sqEntityMapper;

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

        String cpuCode = param.getCpuCode();

        SQEntityPlus sqEntity= new SQEntityPlus();

        Integer sortNum = 0;

        //读出缓存判断，排序
        List<SQEntityPlus> sqCacheList = SqCache.getSqCacheList();
        ArrayList<Integer> sortNumList = new ArrayList<>();
        for (SQEntityPlus entity : sqCacheList) {
            if (param.getUnitCode().equals(entity.getUnitCode())) {
                sortNumList.add(entity.getSortNum());
            }
        }

        if (sortNumList.size() > 0) {
            Collections.sort(sortNumList);
            sortNum = sortNumList.get(sortNumList.size() - 1);
            sortNum++;
        }

        //授权的UnitCode一定是有规则的，例如：最上面的服务器是hb,下一级hb_wh,hb_wh_hk,最下级的客户端服务器也是hb_wh_hk；
        // 当前的节点服务器和该节点的下级服务器（客户端服务器）UnitCode一致，只是SortNum不同，节点是0，其他自动在上一个数值上加1
        sqEntity.setUsername(param.getUsername());
        sqEntity.setUnitCode(param.getUnitCode());
        sqEntity.setSqDay(param.getSqDay());
        sqEntity.setSortNum(sortNum);//排序
        sqEntity.setServerType(param.getServerType());
        sqEntity.setForeverBool(param.getForeverBool());//是否永久授权
        sqEntity.setClientName(param.getClientName());
        sqEntity.setSqsize(param.getSqsize());
        sqEntity.setCompanyname(param.getCompanyname());
        sqEntity.setCompanymsg(param.getCompanymsg());

        sqEntity.setGnlist(param.getGnlist()); //通过集合转成字符串，以|的方式分割
        sqEntity.setStartTime(DateUtil.getDateAndMinute());
        sqEntity.setSsid(OpenUtil.getUUID_32());

        String[] cpuCodelist = cpuCode.split("\\n");

        for (int i = 0; i < cpuCodelist.length; i++) {
            //不等于空，就进入
            String cpucode = cpuCodelist[i];

            if(StringUtils.isNotEmpty(cpucode)){
                if (cpucode.indexOf("|") == -1) {//如果没有就在前面加一个名称编号
                    cpucode = (i + 1) + "|" + cpucode;
                }

                String[] codelist = cpucode.split("\\|");

                sqEntity.setCpuCode(codelist[1]);
                //如果授权码为空，就不授权了
                if(StringUtils.isEmpty(sqEntity.getCpuCode())){
                    result.setMessage("授权码有误，请检查再提交");
                    return;
                }

                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                String startTime = df.format(new Date());

                String sqFileName= PropertiesListenerConfig.getProperty("sq.fileName");
                String path = OpenUtil.getXMSoursePath() + sqFileName + startTime + "\\" + param.getUnitCode();

                System.out.println(path);
                boolean b = CreateSQ.deSQ(sqEntity, path);

                //新增授权码表
                if (b) {
                    SQCode sqCode = new SQCode();
                    sqCode.setName(codelist[0]);
                    sqCode.setSqcode(sqEntity.getCpuCode());
                    sqCode.setRealpath(path + "\\javatrm.ini");
                    sqCode.setSqentityssid(sqEntity.getSsid());
                    sqCode.setSsid(OpenUtil.getUUID_32());
                    Integer insert = sqCodeMapper.insert(sqCode);
                }

            }
        }


        //把授权信息保存到xml文件中，保存到缓存中(为了提高处理速度，用了多线程)
//                    mainService.setSqInfo(sqEntity);
        Integer sqbool = sqEntityMapper.insert(sqEntity);
        result.changeToTrue(sqbool);
        //            NetTool.executeCMD("explorer " + path);//打开文件夹

    }

    public void getBaseType(RResult result) {
        List<BaseType> list = baseTypeMapper.getBaseType();
        result.changeToTrue(list);
    }

    public void getPrivilege(RResult result) {

        /**
         * 授权客户端的功能列表
         * 客户端的功能列表，暂时只有：record_f|asr_f|tts_f|fd_f|ph_f(笔录管理、语音识别、语音播报、设备控制、测谎仪)
         * 单机版/联机版（s_v、o_v）
         * 分支版本：公安、纪委、监察委、法院（ga_t、jw_t、jcw_t、fy_t）
         * OEM版本：通用、HK（common_o、hk_o、nx_o、avst_o）
         * 客户端版/服务端版（c_e、s_e）
         * |cname=avst,cmsg:|
         * @return
         */

        List<BaseGninfo> baseGninfos = baseGnInfoMapper.selectList(null);


//        String filename= PropertiesListenerConfig.getProperty("file.ini.name");
//
//        String path = OpenUtil.getXMSoursePath() + "\\" + filename;
//
//        LinkedHashMap<String, LinkedHashMap<String, String>> map = null;
//        HashMap hashMap = new HashMap();
//        try {
//            hashMap = PrivilegeCache.getPrivilegeList();
//
//            if (null == hashMap || hashMap.size() == 0) {
//                INI4j ini4j = new INI4j(new File(path));
//                map = ini4j.get();
//
//                hashMap.put("serverType", map.get("serverType"));
//                map.remove("serverType");
//                hashMap.put("shouquan", map);
//
//                PrivilegeCache.setPrivilegeCacheList(hashMap);
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        result.changeToTrue(baseGninfos);
    }

    public void delAuthorize(RResult result, GetAuthorizeParam param) {

        String ssid = param.getSsid();

        System.out.println(ssid);

        EntityWrapper<SQEntityPlus> ew = new EntityWrapper<>();
        ew.eq("ssid", ssid);

        SQEntityPlus sqEntityPlus = new SQEntityPlus();
        sqEntityPlus.setClientName(null);
        sqEntityPlus.setSqDay(null);
        sqEntityPlus.setUnitCode(null);
        sqEntityPlus.setSortNum(null);
        sqEntityPlus.setServerType(null);
        sqEntityPlus.setState(0);

        Integer update = sqEntityMapper.update(sqEntityPlus, ew);
        

//        //获取xml保存路径
//        String filename= PropertiesListenerConfig.getProperty("file.data.url");
//
//        //xml保存的固定地址
//        String page = OpenUtil.getXMSoursePath() + filename;
//
//        //把json转换成集合
//        List<String> gnList = SqCache.getSqGnList();
//        List<SQEntityPlus> sqCacheList = SqCache.getSqCacheList();
//        if (null != gnList && gnList.size() > 0 && null != sqCacheList && sqCacheList.size() > 0) {
//
//            for (int i = 0; i < sqCacheList.size(); i++) {
//                SQEntity entity = sqCacheList.get(i);
//                entity.setGnlist(gnList.get(i));
//            }
//            SqCache.setSqCacheList(sqCacheList);
//            SqCache.setSqGnList(null);
//        }
//
//        SqCache.removeSqCacheBySsid(ssid);
//        SQEntityRoom_R_W_XML.writeXml_courtRoomList(page, sqCacheList);
//
//        SqCache.delSqCacheList();//清空缓存

        result.changeToTrue(update);
    }

    public ResponseEntity<Resource> downloadSQFile(String fileName) {

        try {
            //获取系统上一级目录
//            String savePath = OpenUtil.getXMSoursePath();
            //授权文件名称
            String trmFileName = "javatrm.ini";

//            String savePath = "/home/download/";
            // 获取文件名称，中文可能被URL编码
//            fileName = URLDecoder.decode(fileName, "UTF-8");
//            String sqFileName= PropertiesListenerConfig.getProperty("sq.fileName");
            // 获取本地文件系统中的文件资源
//            FileSystemResource resource = new FileSystemResource(savePath + sqFileName + fileName + "\\" + trmFileName);


            SQCode sqCode = new SQCode();
            sqCode.setSsid(fileName);
            sqCode = sqCodeMapper.selectOne(sqCode);

            if (StringUtils.isNotEmpty(sqCode.getRealpath())) {

                FileSystemResource resource = new FileSystemResource(sqCode.getRealpath());

                // 解析文件的 mime 类型
                String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(fileName);
                // 无法判断MIME类型时，作为流类型
                mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
                // 实例化MIME
                MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);

                trmFileName = sqCode.getName() + "_" + sqCode.getSqcode() + "_" + trmFileName;

                /*
                 * 构造响应的头
                 */
                HttpHeaders headers = new HttpHeaders();
                // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
                String filenames = new String(trmFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                headers.setContentDispositionFormData("attachment", filenames);
                headers.setContentType(mediaType);

                /*
                 * 返还资源
                 */
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.getInputStream().available())
                        .body(resource);
            }

            LogUtil.intoLog(4, this.getClass(), "读取授权码错误！请检查ssid是否存在");
        } catch (IOException e) {
//            e.printStackTrace();
            LogUtil.intoLog(4, e.getClass(), "文件不存在或文件读写错误");
        }
        return null;
    }

    public void getFindByssid(RResult result, GetAuthorizeParam param) {

        String ssid = param.getSsid();

        SQEntityPlus sqEntityPlus = sqEntityMapper.getFindByssid(ssid);

        String gn = sqEntityPlus.getGnlist();
        //判断是哪个就替换成中文
        gn = mainService.getZW(gn);
        gn = gn.replace("|", "，");
        int indexOf = gn.lastIndexOf("，");
        gn = gn.substring(0, indexOf);

        sqEntityPlus.setGnlist(gn);

        result.changeToTrue(sqEntityPlus);
    }
}
