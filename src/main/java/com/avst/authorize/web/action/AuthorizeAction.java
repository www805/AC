package com.avst.authorize.web.action;

import com.avst.authorize.common.config.check.Create;
import com.avst.authorize.common.config.check.Delete;
import com.avst.authorize.common.utils.DateUtil;
import com.avst.authorize.common.utils.RResult;
import com.avst.authorize.web.req.GetAuthorizeParam;
import com.avst.authorize.web.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/ac")
public class AuthorizeAction {

    @Autowired
    private AuthorizeService authorizeService;


    /**
     * 添加授权
     * @param param
     * @return
     */
    @PostMapping(value = "/addAuthorize")
    public RResult addAuthorize(@RequestBody @Validated(Create.class) GetAuthorizeParam param) {
        RResult result = new RResult();
        authorizeService.addAuthorize(result, param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 删除一条授权记录
     * @param param
     * @return
     */
    @RequestMapping(value = "/delAuthorize")
    public RResult delAuthorize(@RequestBody @Validated(Delete.class) GetAuthorizeParam param) {
        RResult result = new RResult();
        authorizeService.delAuthorize(result, param);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /**
     * 获取全部权限
     * @return
     */
    @RequestMapping(value = "/getPrivilege")
    public RResult getPrivilege() {
        RResult result = new RResult();
        authorizeService.getPrivilege(result);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }


    /***
     * 授权文件上传接口
     * @return
     */
    @PostMapping(value = "/uploadBytxt")
    public RResult uploadBytxt(@RequestParam("file") MultipartFile file) {
        RResult result = new RResult();
        authorizeService.uploadBytxt(result, file);
        result.setEndtime(DateUtil.getDateAndMinute());
        return result;
    }

    /**
     * 授权文件下载 {fileName:.*}
     * @param fileName
     * @return
     */
    @RequestMapping("/downloadSQFile/{fileName:.*}")
    public ResponseEntity<Resource> downloadSQFile(@PathVariable("fileName") String fileName) {
        return authorizeService.downloadSQFile(fileName);
    }



    /**
     * 授权文件下载2
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
//    @RequestMapping("/download")
//    public String downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
//        String filename="2.xlsx";
//        String filePath = "D:/download" ;
//        File file = new File(filePath + "/" + filename);
//        if(file.exists()){ //判断文件父目录是否存在
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            // response.setContentType("application/force-download");
//            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null; //文件输入流
//            BufferedInputStream bis = null;
//
//            OutputStream os = null; //输出流
//            try {
//                os = response.getOutputStream();
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                int i = bis.read(buffer);
//                while(i != -1){
//                    os.write(buffer);
//                    i = bis.read(buffer);
//                }
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            System.out.println("----------file download---" + filename);
//            try {
//                bis.close();
//                fis.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}
