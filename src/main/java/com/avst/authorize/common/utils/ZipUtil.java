package com.avst.authorize.common.utils;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.cs.ext.GBK;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.io.File;
import java.io.FileOutputStream;

public class ZipUtil {

    public static final String SUFFIX_NAME = "zip";

    public static void main(String[] args) {
//        packageZipFolder();

//        decompression("D:\\java\\AC\\sq\\tempdonwload\\aaaaaaaaa.rar", "D:\\java\\AC\\sq\\tempdonwload\\", null);


//        File file = new File("D:\\java\\AC\\sq\\tempdonwload\\sq1.rar");


        try {
//            unrar("D:\\java\\AC\\sq\\tempdonwload\\donwload.rar", "D:\\java\\AC\\sq\\tempdonwload\\");


//            String entry = "D:\\java\\AC\\sq\\shouquan\\";//需要压缩的文件目录
            String entry = "D:\\java\\AC\\sq\\tempshouquan\\";//需要压缩的文件目录
            File file = new File(entry);

            System.out.println(file.getAbsolutePath()+ "\\sqq.zip");

//            ZipOutputStream zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath() + "sqq.zip")));
//
//            String base = file.getName();
//
//            compressZip(zipOutput, file, base);
//            zipOutput.closeEntry();
//            zipOutput.close();

//            compressAllFileZip(entry,"D:\\java\\AC\\sq\\tempshouquan\\123","可以吗？？？骚灵情歌");

            decompression(file.getAbsolutePath() + "\\sqq.zip", entry, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 包括
     * @param basePath 需要压缩的文件目录
     * @param zipName 压缩后存放的路径
     * @param comment 压缩注释
     */
    public static void compressAllFileZip(String basePath, String zipName, String comment){

        File file = new File(basePath);//需要压缩的文件目录
        File zipFile = new File(zipName);//"e:" + File.separator + "zipFile.zip"
        if (!file.exists()){
            LogUtil.intoLog(4, ZipUtil.class, "压缩文件夹不存在。。。。。。");
            return;
        }
        if (!zipFile.exists()){ //如果不存在就创建文件夹，结束后删除该文件
            zipFile.mkdir();
        }

        ZipOutputStream zipOutput = null;
        try {
            zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile.getAbsolutePath() + ".zip")));
            zipOutput.setComment(comment);

            String base = file.getName();

            compressZip(zipOutput, file, base);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != zipOutput) {
                try {
                    zipOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            zipFile.delete();
        }

    }


    /**
     * 因为子文件夹中可能还有文件夹，所以进行递归
     *
     */
    private static void compressZip(ZipOutputStream zipOutput, File file, String base) throws IOException {

        if(file.isDirectory()){
            File[] listFiles = file.listFiles();// 列出所有的文件

            for(File fi : listFiles){
                if(fi.isDirectory()){
                    compressZip(zipOutput, fi, base + "/" + fi.getName());
                }else{
                    zzip(zipOutput, fi, base);
                }
            }
        }else{
            zzip(zipOutput, file, base);
        }
    }


    /**
     * 压缩的具体操作
     *
     */
    private static void zzip(ZipOutputStream zipOutput, File file, String base) throws IOException, FileNotFoundException {
        ZipEntry zEntry = new ZipEntry(base + File.separator + file.getName());
        zipOutput.putNextEntry(zEntry);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        byte[] buffer = new byte[1024];
        int read = 0;
        while((read =bis.read(buffer)) != -1){
            zipOutput.write(buffer, 0, read);
        }
        bis.close();
    }

    /**
     * 把一个文件夹压缩成压缩包，保存到指定位置
     * @param folder  压缩文件夹路径
     * @param zipName  压缩完成zip保存路径
     * @param comment  压缩说明
     */
    public static void packageZipFolder(String folder, String zipName, String comment) {
        // 要被压缩的文件夹

        File file = new File(folder);//"e:" + File.separator + "xiao"
        File zipFile = new File(zipName);//"e:" + File.separator + "zipFile.zip"
            if (!file.exists()){
            LogUtil.intoLog(4, ZipUtil.class, "压缩文件夹不存在。。。。。。");
            return;
        }

        InputStream input = null;
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
//            comment = new String(comment.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            zipOut.setComment(comment);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; ++i) {
                    input = new FileInputStream(files[i]);
                    zipOut.putNextEntry(new ZipEntry(file.getName()
                            + File.separator + files[i].getName()));
                    int temp = 0;
                    while ((temp = input.read()) != -1) {
                        zipOut.write(temp);
                    }
                    input.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != zipOut) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != input){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把一个文件压缩成压缩包，保存到指定位置
     * @param filepath  压缩文件路径
     * @param zipName  压缩完成zip保存路径
     * @param comment  压缩说明
     */
    public static void packageZipOne(String filepath, String zipName, String comment) {
        InputStream input = null;
        ZipOutputStream zipOut = null;

        File file = new File(filepath);//"e:" + File.separator + "aaa.txt"
        File zipFile = new File(zipName);//"e:" + File.separator + "hello.zip"

        if (!file.exists()){
            LogUtil.intoLog(4, ZipUtil.class, "压缩文件夹不存在。。。。。。");
            return;
        }

        try {
            input = new FileInputStream(file);
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            // 设置注释
//            comment = new String(comment.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            zipOut.setComment(comment);//"把授权文件放到要授权的机器使用即可"
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != zipOut) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 解压rar
     * @param sourceRarPath 需要解压的rar文件全路径
     * @param destDirPath 需要解压到的文件目录
     * @throws Exception
     */
    public static void unrar(String sourceRarPath, String destDirPath) throws Exception {
        File sourceRar=new File(sourceRarPath);
        File destDir=new File(destDirPath);
        Archive archive = null;
        FileOutputStream fos = null;
        System.out.println("Starting 开始解压...");
            try {
                archive = new Archive(sourceRar,null);
                FileHeader fh = archive.nextFileHeader();
                int count = 0;
                File destFileName = null;
                while (fh != null) {
                System.out.println((++count) + ") " + fh.getFileNameString());
                String compressFileName = fh.getFileNameString().trim();
                destFileName = new File(destDir.getAbsolutePath() + "/" + compressFileName);
                if (fh.isDirectory()) {
                if (!destFileName.exists()) {
                    destFileName.mkdirs();
                }
                fh = archive.nextFileHeader();
                continue;
                }
                if (!destFileName.getParentFile().exists()) {
                    destFileName.getParentFile().mkdirs();
                }

                    fos = new FileOutputStream(destFileName);
                    archive.extractFile(fh, fos);
                    fos.close();
                    fos = null;
                    fh = archive.nextFileHeader();
                }

                archive.close();
                archive = null;
                System.out.println("Finished 解压完成!");
              } catch (Exception e) {
                    throw e;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                        fos = null;
                    } catch (Exception e) {
                    }
                }
                if (archive != null) {
                    try {
                        archive.close();
                        archive = null;
                    } catch (Exception e) {
                    }
                }
            }
    }



    /**
     * 解压文件
     * filePath所代表文件系统不能与targetStr一致
     *
     * @param filePath  压缩文件路径
     * @param targetStr 解压至所在文件目录
     * @param updatefile 经过上传过来的解压文件
     */
    public static void decompression(String filePath, String targetStr, MultipartFile updatefile) {

        InputStream inputStream = null;

        ZipInputStream zis = null;
        BufferedOutputStream bos = null;
        try {
            if (StringUtils.isNotEmpty(filePath) && null == updatefile) {
                int inZip = isCheckFileNameInZip(filePath);
                if (inZip <= 0) {
                    return;
                }
                File source = new File(filePath);
                if (!source.exists()) {
                    LogUtil.intoLog(4, ZipUtil.class, "文件不存在，解压失败");
                    return;
                }
                inputStream = new FileInputStream(source);
            }else{
                inputStream = updatefile.getInputStream();
            }

            zis = new ZipInputStream(inputStream);
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                File target = new File(targetStr, entry.getName());
                if (!target.getParentFile().exists()) {
                    target.getParentFile().mkdirs();// 创建文件父目录
                }
                // 写入文件
                bos = new BufferedOutputStream(new FileOutputStream(target));
                int read = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.flush();
            }
            zis.closeEntry();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(zis, bos, inputStream);
        }

    }





    /**
     *  校验目标文件名称是否是zip格式
     *  @param zipFile
     *  @return
     */
    private static int isCheckFileNameInZip(String zipFile){
        int las = zipFile.lastIndexOf(".");
        if(las == -1){
//            throw new RuntimeException( zipFile + " is not zip format! this format = ??? ");
            LogUtil.intoLog(4, ZipUtil.class, zipFile + " is not zip format! this format = ??? ");
        }
        String format = zipFile.substring(las + 1);
        if(!SUFFIX_NAME.equalsIgnoreCase(format)){
//            throw new RuntimeException( zipFile + " is not zip format! this format = " + format);
            LogUtil.intoLog(4, ZipUtil.class, zipFile + " is not zip format! this format = " + format);
            return -1;
        }
        return las;
    }


    /**
     * 关闭一个或多个流对象
     * @param closeables
     * 可关闭的流对象列表
     */
    public static void closeQuietly(Closeable... closeables) {
        try {
            if (closeables != null && closeables.length > 0) {
                for (Closeable closeable : closeables) {
                    if (closeable != null) {
                        closeable.close();
                    }
                }
            }
        } catch (IOException e) {
            // do nothing
            e.printStackTrace();
        }
    }


    /**
     * 解压到指定目录
     */
    public static void unZipFiles(String zipPath,String descDir)throws IOException
    {
        unZipFiles(new File(zipPath), descDir);
    }
    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile,String descDir)
    {
        File pathFile = new File(descDir);
        if(!pathFile.exists()) {
            pathFile.mkdirs();
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            //解决zip文件中有中文目录或者中文文件
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                in = zip.getInputStream(entry);
                String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if(!file.exists()){
                    file.mkdirs();
                }
                outPath = outPath.replace("/", "\\");
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()) {
                    continue;
                }
                //输出文件路径信息
                System.out.println(outPath);
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while((len=in.read(buf1))>0)
                {
                    out.write(buf1,0,len);
                }

            }
            System.out.println("******************解压完毕********************");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }





    /**
     * 解压.zip文件
     * @param sourceFile 压缩包路径
     * @param outputDir 解压到目的录
     * @throws IOException
     */
    public static void unZip(String sourceFile, String outputDir) throws IOException {
        ZipFile zipFile = null;
        File file = new File(sourceFile);
        try {
            Charset CP866 = Charset.forName("CP866");  //specifying alternative (non UTF-8) charset
            zipFile =  new ZipFile(file, CP866);
            createDirectory(outputDir,null);//创建输出目录

            Enumeration<?> enums = zipFile.entries();
            while(enums.hasMoreElements()){

                ZipEntry entry = (ZipEntry) enums.nextElement();
                System.out.println("解压." +  entry.getName());

                if(entry.isDirectory()){//是目录
                    createDirectory(outputDir,entry.getName());//创建空目录
                }else{//是文件
                    File tmpFile = new File(outputDir + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/",null);//创建输出目录

                    InputStream in = null;
                    OutputStream out = null;
                    try{
                        in = zipFile.getInputStream(entry);;
                        out = new FileOutputStream(tmpFile);
                        int length = 0;

                        byte[] b = new byte[2048];
                        while((length = in.read(b)) != -1){
                            out.write(b, 0, length);
                        }

                    }catch(IOException ex){
                        throw ex;
                    }finally{
                        if(in!=null)
                            in.close();
                        if(out!=null)
                            out.close();
                    }
                }
            }

        } catch (IOException e) {
            throw new IOException("解压缩文件出现异常",e);
        } finally{
            try{
                if(zipFile != null){
                    zipFile.close();
                }
            }catch(IOException ex){
                throw new IOException("关闭zipFile出现异常",ex);
            }
        }
    }



    /**
     * 构建目录
     * @param outputDir
     * @param subDir
     */
    public static void createDirectory(String outputDir,String subDir){
        File file = new File(outputDir);
        if(!(subDir == null || subDir.trim().equals(""))){//子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if(!file.exists()){
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }
}
