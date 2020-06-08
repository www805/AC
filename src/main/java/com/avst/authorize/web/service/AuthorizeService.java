package com.avst.authorize.web.service;

import com.avst.authorize.common.cache.BaseGnInfoCache;
import com.avst.authorize.common.cache.SqCache;
import com.avst.authorize.common.entity.*;
import com.avst.authorize.common.utils.*;
import com.avst.authorize.common.utils.properties.PropertiesListenerConfig;
import com.avst.authorize.common.utils.sq.CreateSQ;
import com.avst.authorize.common.utils.sq.SQEntity;
import com.avst.authorize.web.mapper.*;
import com.avst.authorize.web.req.GetAuthorizeListParam;
import com.avst.authorize.web.req.GetAuthorizeParam;
import com.avst.authorize.web.req.UpdateAuthorizeTimeParam;
import com.avst.authorize.web.vo.GetAuthorizeListVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wb.deencode.DeCodeUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthorizeService {

    @Autowired
    private BaseGnTypeMapper baseGnTypeMapper;

    @Autowired
    private BaseGnInfoMapper baseGnInfoMapper;

    @Autowired
    private SQCodeMapper sqCodeMapper;

    @Autowired
    private SQEntityMapper sqEntityMapper;

    @Autowired
    private BaseTypeMapper baseTypeMapper;

    public void uploadBytxt(RResult result, MultipartFile file) {

        if (file.isEmpty()) {
            result.setMessage("上传失败，请选择文件");
            return;
        }

        result.setMessage("授权文件解析失败，请检查是否是合法的授权文件...");
        InputStream inputStream = null;
        String cpuCode = "";

        String contentType = file.getContentType();
        if("text/plain".equals(contentType)){
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
        }else if(file.getOriginalFilename().endsWith(".ini")){
            try {
                inputStream = file.getInputStream();

                byte[] buf = new byte[1024];
                int length = 0;
                while((length = inputStream.read(buf)) != -1){
                    cpuCode += new String(buf, 0, length);
                    System.out.print(cpuCode);
                }

                try {
                    String[] sqcodearr= DeCodeUtil.decoderByDES(cpuCode).split(";");
                    if(sqcodearr.length > 2){
                        cpuCode = sqcodearr[3];
                    }else{
                        cpuCode = "";
                    }
                } catch (Exception e) {
                    cpuCode = "";
                    LogUtil.intoLog(4, this.getClass(), "解析失败！");
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
        }else if("application/zip".equals(contentType)){
            //先批量解压
            String tempdonwloadName= PropertiesListenerConfig.getProperty("sq.tempshouquan");
            String tempPath = OpenUtil.getXMSoursePath() + tempdonwloadName;
            FileUtil.delAllFile(tempPath);
            ZipUtil.decompression(null, tempPath, file);
            //解析txt文件里的授权码
            cpuCode = traverseFolder(tempPath);
        }else if("application/octet-stream".equals(contentType)){

        }

        if (StringUtils.isNotEmpty(cpuCode)) {
            result.changeToTrue(cpuCode.trim());
        }

    }

    @Cacheable(cacheNames = "emp", key = "#param.clientName+'-'+#param.username+'-'+#param.sqcode+'-'+#param.batypessid+'-'+#param.currPage+'-'+#param.pageSize")
    public RResult getAuthorizeList(RResult result, GetAuthorizeListParam param) {

        GetAuthorizeListVO authorizeListVO = new GetAuthorizeListVO();

        EntityWrapper<SQEntityPlus> ew = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(param.getUsername())){
            ew.like("s.username", param.getUsername());
        }
        if(StringUtils.isNotEmpty(param.getClientName())){
            ew.like("s.clientname", param.getClientName());
        }
        if(StringUtils.isNotEmpty(param.getSqcode())){
            ew.like("a.sqcode", param.getSqcode());
        }
        if (StringUtils.isNotEmpty(param.getBatypessid()) && !"0".equals(param.getBatypessid())) {
            ew.eq("s.batypessid", param.getBatypessid());
        }

        ew.eq("s.state", 1);//1是正常状态0是已删除的

        ew.orderBy("s.startTime", false);

        Integer count = sqEntityMapper.getSQCount(ew);
        param.setRecordCount(count);

        com.baomidou.mybatisplus.plugins.Page<SQEntityPlus> page = new Page<>(param.getCurrPage(), param.getPageSize());
        List<SQEntityPlus> sqCacheList = sqEntityMapper.getSQListPage(page, ew);

        for (SQEntityPlus sqEntity : sqCacheList) {
            String gn = sqEntity.getGnlist();
            //判断是哪个就替换成中文
            gn = getZW(gn);
            sqEntity.setGnlist(gn);
        }

        authorizeListVO.setPagelist(sqCacheList);
        authorizeListVO.setPageparam(param);

        result.changeToTrue(authorizeListVO);
        return result;
    }

    @Transactional
    @CacheEvict(cacheNames = {"emp", "empeles","empserverbase","emppaihb","empyear"}, allEntries = true)
    public void addAuthorize(RResult result, GetAuthorizeParam param) {

        String cpuCode = param.getCpuCode();

        SQEntityPlus sqEntity = new SQEntityPlus();

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
        if (StringUtils.isNotEmpty(param.getServerType())) {
            sqEntity.setServerType(param.getServerType());
        } else {
            sqEntity.setServerType("police");
        }
        sqEntity.setForeverBool(param.getForeverBool());//是否永久授权
        sqEntity.setClientName(param.getClientName());
        sqEntity.setSqsize(param.getSqsize());
        sqEntity.setCompanyname(param.getCompanyname());
        sqEntity.setCompanymsg(param.getCompanymsg());
        sqEntity.setBatypessid(param.getBatypessid());

        sqEntity.setGnlist(param.getGnlist()); //通过集合转成字符串，以|的方式分割
        sqEntity.setStartTime(DateUtil.getDateAndMinute());
        sqEntity.setSsid(OpenUtil.getUUID_32());

        String[] cpuCodelist = cpuCode.split("\\n");

        for (int i = 0; i < cpuCodelist.length; i++) {
            //不等于空，就进入
            String cpucode = cpuCodelist[i];

            if (StringUtils.isNotEmpty(cpucode)) {
                if (cpucode.indexOf("|") == -1) {//如果没有就在前面加一个名称编号
                    cpucode = (i + 1) + "|" + cpucode;
                }

                String[] codelist = cpucode.split("\\|");

                sqEntity.setCpuCode(codelist[1]);
                //如果授权码为空，就不授权了
                if (StringUtils.isEmpty(sqEntity.getCpuCode())) {
                    result.setMessage("授权码有误，请检查再提交");
                    return;
                }

//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//                String startTime = df.format(new Date());

                Calendar now = Calendar.getInstance();
                String year = now.get(Calendar.YEAR) + "";
                String month = ((now.get(Calendar.MONTH) + 1) + "").length() == 1 ? "0" + (now.get(Calendar.MONTH) + 1) : (now.get(Calendar.MONTH) + 1) + "";
                String day = (now.get(Calendar.DAY_OF_MONTH) + "").length() == 1 ? "0" + now.get(Calendar.DAY_OF_MONTH) : now.get(Calendar.DAY_OF_MONTH) + "";

                String sqFileName = PropertiesListenerConfig.getProperty("sq.fileName");
                String path = OpenUtil.getXMSoursePath() + sqFileName + year + "\\" + month + "\\" + day + "\\" + param.getUnitCode() + "\\" + param.getCpuCode() + "_" + System.currentTimeMillis();

                LogUtil.intoLog("授权路径："+path);
                boolean b = CreateSQ.deSQ(sqEntity, path);

                String javatrm = PropertiesListenerConfig.getProperty("sq.javatrm");

                //新增授权码表
                if (b) {
                    SQCode sqCode = new SQCode();
                    sqCode.setName(codelist[0]);
                    sqCode.setSqcode(sqEntity.getCpuCode());
                    sqCode.setRealpath(path + "\\" + javatrm);
                    sqCode.setSqentityssid(sqEntity.getSsid());
                    sqCode.setStartTime(sqEntity.getStartTime());
                    sqCode.setSqDay(sqEntity.getSqDay());
                    sqCode.setSsid(OpenUtil.getUUID_32());
                    Integer insert = sqCodeMapper.insert(sqCode);
                }

            }
        }


        //把授权信息保存到xml文件中，保存到缓存中(为了提高处理速度，用了多线程)
//                    mainService.setSqInfo(sqEntity);
        Integer sqbool = sqEntityMapper.insert(sqEntity);
        result.changeToTrue(sqbool);
        result.setMessage("添加授权成功！");
        //            NetTool.executeCMD("explorer " + path);//打开文件夹

    }

    public void getBaseType(RResult result) {
        List<BaseGnType> list = baseGnTypeMapper.getBaseNgType();
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

        List<BaseGninfo> baseGninfos = BaseGnInfoCache.getBaseGninfoListCache();

        result.changeToTrue(baseGninfos);
    }

    @CacheEvict(cacheNames = {"emp", "empeles","empserverbase","emppaihb","empyear"}, allEntries = true)
    public void delAuthorize(RResult result, GetAuthorizeParam param) {

        String ssid = param.getSsid();

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

        LogUtil.intoLog(1, this.getClass(), " 删除一条授权：" + ssid);

        result.changeToTrue(update);
        result.setMessage("删除一条授权信息成功！");
    }

    public ResponseEntity<Resource> downloadSQFile(String fileName) {

//        SQCode sqCode = new SQCode();
//        sqCode.setSsid(fileName);
//        sqCode = sqCodeMapper.selectOne(sqCode);
//        if(null == sqCode){
//            LogUtil.intoLog(4, this.getClass(), "读取授权码错误！请检查ssid是否存在" + fileName);
//            return null;
//        }
//
//        return downloadAllSQFile(sqCode.getSqentityssid());

        String tempdonwloadName= PropertiesListenerConfig.getProperty("sq.tempshouquan");
        String donwloadName= PropertiesListenerConfig.getProperty("sq.shouquan");
        String tempPath = OpenUtil.getXMSoursePath() + tempdonwloadName;
        String sqFilePath = OpenUtil.getXMSoursePath() + donwloadName;

        FileUtil.delAllFile(sqFilePath);//删除文件夹里所有内容

        try {
            //获取系统上一级目录
//            String savePath = OpenUtil.getXMSoursePath();
            //授权文件名称
            String trmFileName = PropertiesListenerConfig.getProperty("sq.javatrm");

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

                String tagerZip = tempPath + sqCode.getName() + "_" + sqCode.getSqcode() + "_" + trmFileName;
                SQEntityPlus entityPlus = sqEntityMapper.getFindByssid(sqCode.getSqentityssid());
                if(null != entityPlus){
                    tagerZip = tempPath + entityPlus.getUsername() + "_" + entityPlus.getCompanyname() + "_" + entityPlus.getSsid();// + ".zip" getStartTime
                }

                FileUtil.delAllFile(tagerZip + ".zip");
                String path = sqFilePath + sqCode.getSqcode() + "\\" + trmFileName;
                File tagerFile = new File(path);
                File yuanFile = new File(sqCode.getRealpath());

                LogUtil.intoLog(1, this.getClass(), "path路径:" + path);
                LogUtil.intoLog(1, this.getClass(), "tagerZip路径:" + tagerZip);

                FileUtils.copyFile(yuanFile, tagerFile);
                ZipUtil.compressAllFileZip(sqFilePath, tagerZip, "把授权文件放到要授权的机器使用即可");


                FileSystemResource resource = new FileSystemResource(tagerZip + ".zip");

                // 解析文件的 mime 类型
                String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(fileName);
                // 无法判断MIME类型时，作为流类型
                mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
                // 实例化MIME
                MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);

                trmFileName = entityPlus.getUsername() + "_" + entityPlus.getCompanyname() + "_" + entityPlus.getStartTime() + ".zip";

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
        gn = getZW(gn);
        gn = gn.replace("|", "，");
        int indexOf = gn.lastIndexOf("，");
        gn = gn.substring(0, indexOf);

        sqEntityPlus.setGnlist(gn);

        result.changeToTrue(sqEntityPlus);
    }

    public synchronized ResponseEntity<Resource> downloadAllSQFile(String ssid) {

        try {
            //获取系统上一级目录
            String tempdonwloadName= PropertiesListenerConfig.getProperty("sq.tempshouquan");
            String donwloadName= PropertiesListenerConfig.getProperty("sq.shouquan");
            String tempPath = OpenUtil.getXMSoursePath() + tempdonwloadName;
            String sqFilePath = OpenUtil.getXMSoursePath() + donwloadName;

//            FileUtil.delAllFile(tempPath);//删除文件夹里所有内容
            FileUtil.delAllFile(sqFilePath);//删除文件夹里所有内容

//            FileUtils.forceDelete(tempFile);
//            FileUtils.forceDelete(sqfile);

            File tempFile = new File(tempPath);
//            File sqfile = new File(sqFilePath);
            if(!tempFile.exists()){
                tempFile.mkdirs();
            }

            String sqFileName = PropertiesListenerConfig.getProperty("sq.javatrm");

            SQCode sqCode = new SQCode();
            SQEntityPlus entityPlus = sqEntityMapper.getFindByssid(ssid);

//            String uuid_32 = OpenUtil.getUUID_32();

            if (null != entityPlus) {
                String tagerZip = tempPath + entityPlus.getUsername() + "_" + entityPlus.getCompanyname() + "_" + entityPlus.getSsid() ;//+ ".zip"

                List<SQCode> sqCodeList = entityPlus.getSqCodeList();
                if (sqCodeList.size() > 0) {
                    sqCode = sqCodeList.get(0);
                }
                //判断压缩包是否已经存在，如果存在就直接使用，不存在就进行压缩
//                File fileZIP = new File(tagerZip + ".zip");
//                if(!fileZIP.exists()){
//                }
                for (SQCode code : sqCodeList) {
                    String path = sqFilePath + code.getSqcode() + "\\" + sqFileName;
                    File tagerFile = new File(path);
                    File yuanFile = new File(code.getRealpath());

                    FileUtils.copyFile(yuanFile, tagerFile);
                }

                ZipUtil.compressAllFileZip(sqFilePath, tagerZip, "把授权文件放到要授权的机器使用即可");


                FileSystemResource resource = new FileSystemResource(tagerZip + ".zip");

                // 解析文件的 mime 类型
                String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(ssid);
                // 无法判断MIME类型时，作为流类型
                mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
                // 实例化MIME
                MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);

                sqFileName = entityPlus.getUsername() + "_" + entityPlus.getCompanyname() + "_" + entityPlus.getStartTime() + ".zip";

                /*
                 * 构造响应的头
                 */
                HttpHeaders headers = new HttpHeaders();
                // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
                String filenames = new String(sqFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
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

            LogUtil.intoLog(4, this.getClass(), "读取授权码错误！请检查ssid是否存在" + ssid);
        } catch (IOException e) {
            LogUtil.intoLog(4, e.getClass(), "文件不存在或文件读写错误");
            e.printStackTrace();
        }
        return null;

    }


    //转换成中文
    public String getZW(String str){
        RResult result = new RResult();
        getPrivilege(result);

        List<BaseGninfo> shouquan = (List<BaseGninfo>) result.getData();

        for (BaseGninfo gninfo : shouquan) {
            if (str.indexOf(gninfo.getName()) != -1) {
                str = str.replace(gninfo.getName(), gninfo.getTitle());
            }
        }
        return str;
    }

    public String traverseFolder(String path) {

        String sqlistStr = "";
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                LogUtil.intoLog(4, this.getClass(), "文件夹是空的!");
                return sqlistStr;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
                        if (file2.getAbsolutePath().indexOf(".txt") != -1) {
//                            System.out.println("解析txt文件");

                            BufferedReader buffReader = null;
                            String cpuCode = "";

                            try {
                                buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2.getAbsolutePath())));
                                String strTmp = "";
                                while((strTmp = buffReader.readLine())!=null){
                                    cpuCode += strTmp;
                                }

                                String filenameFilter = file2.getName().replace(".txt", "");

                                sqlistStr += filenameFilter + "|" + cpuCode + "\n";

//                                System.out.println(sqlistStr);
                                LogUtil.intoLog(1, this.getClass(), sqlistStr);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if(null != buffReader){
                                    try {
                                        buffReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            LogUtil.intoLog(4, this.getClass(), "文件不存在!");
        }

        return sqlistStr;
    }

    @Transactional
    @CacheEvict(cacheNames = "emp", allEntries = true)
    public void updateAuthorizeTime(RResult result, UpdateAuthorizeTimeParam param) {

        String xqCpuCode = param.getXqCpuCode().trim();

        EntityWrapper<SQEntityPlus> ew = new EntityWrapper<>();
        ew.eq("cpuCode", xqCpuCode);

        List<SQEntityPlus> sqEntityPlusList = sqEntityMapper.selectList(ew);
        if(sqEntityPlusList.size() > 1){

            result.setData(sqEntityPlusList);

            result.setMessage("找到多個授權碼，要收取哪個？");
            return;
        }else if (sqEntityPlusList.size() == 1) {

            SQEntityPlus sqEntityPlus = sqEntityPlusList.get(0);

            Date date = null;
            SQCode sqCode = null;
            EntityWrapper<SQCode> cew = new EntityWrapper<>();
            cew.eq("sqentityssid", sqEntityPlus.getSsid());
            cew.eq("sqcode", param.getXqCpuCode());
            List<SQCode> sqCodes = sqCodeMapper.selectList(cew);

            if(sqCodes.size() == 1){
                sqCode = sqCodes.get(0);
                FileUtil.delAllFile(sqCode.getRealpath());
            }else{
                result.setMessage("没找到该授权码，请确认授权码是否已经授权");
                return;
            }

            if(null != sqCode && StringUtils.isBlank(sqCode.getStartTime())){
                sqCode.setStartTime(sqEntityPlus.getStartTime());
            }
            if (null != sqCode && null == sqCode.getSqDay() || null != sqCode && sqCode.getSqDay() == 0) {
                sqCode.setSqDay(sqEntityPlus.getSqDay());
            }

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                date = format2.parse(sqCode.getStartTime());

                Calendar calendar = Calendar.getInstance();///.DAY_OF_MONTH, 1);//天数加一
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, sqCode.getSqDay());//日历对象

                String DateToStr2 = format2.format(calendar.getTime());

                int endTime = calLastedTime(DateToStr2);
                //如果小于0代表未过期
                if(endTime > 0){
                    //已过期
                    sqEntityPlus.setStartTime(DateUtil.getDateAndMinute());
                    sqEntityPlus.setSqDay(param.getXqSqDay());
                    sqCode.setStartTime(DateUtil.getDateAndMinute());
                    sqCode.setSqDay(param.getXqSqDay());
                }else{
                    //未过期
                    sqCode.setSqDay(sqCode.getSqDay() + param.getXqSqDay());//时间续期
                    sqEntityPlus.setSqDay(sqEntityPlus.getSqDay() + param.getXqSqDay());//时间续期
                }



            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar now = Calendar.getInstance();
            String year = now.get(Calendar.YEAR) + "";
            String month = ((now.get(Calendar.MONTH) + 1) + "").length() == 1 ? "0" + (now.get(Calendar.MONTH) + 1) : (now.get(Calendar.MONTH) + 1) + "";
            String day = (now.get(Calendar.DAY_OF_MONTH) + "").length() == 1 ? "0" + now.get(Calendar.DAY_OF_MONTH) : now.get(Calendar.DAY_OF_MONTH) + "";

            String sqFileName= PropertiesListenerConfig.getProperty("sq.fileName");
            String javatrm= PropertiesListenerConfig.getProperty("sq.javatrm");
            String path = OpenUtil.getXMSoursePath() + sqFileName + year + "\\" + month + "\\" + day + "\\" + param.getXqCpuCode();

            boolean delete = FileUtil.delAllFile(path);
            boolean b = CreateSQ.deSQ(sqEntityPlus, path);

            EntityWrapper<SQEntityPlus> sqew = new EntityWrapper<>();
            sqew.eq("ssid", sqEntityPlus.getSsid());
            sqEntityMapper.update(sqEntityPlus, sqew);


            EntityWrapper<SQCode> scew = new EntityWrapper<>();
            scew.eq("ssid", sqCode.getSsid());
            sqCode.setRealpath(path + "\\" + javatrm);
            int update = sqCodeMapper.update(sqCode, scew);
            if(update > 0){
                result.setMessage(sqEntityPlus.getCpuCode() + "续期成功！");
                if(null != sqCode){
                    result.changeToTrue(sqCode.getSsid());
                }else{
                    result.changeToTrue();
                }
            }
        }else{
            result.setMessage("没找到该授权码，请确认授权码是否已经授权");
        }


    }


    //判断两个时间相差几秒
    public  int calLastedTime(String lasttime) {
        long nowTime = new Date().getTime();
        int reqsultNum = 0;

        DateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse = null;
        try {
            parse = formatter.parse(lasttime);

            long paramTime = parse.getTime();
            reqsultNum = (int)((nowTime - paramTime) / 1000);

        } catch (ParseException e) {
            LogUtil.intoLog(4, this.getClass(), "判断两个时间相差出错。。");
        }
        return reqsultNum;
    }

}
