package com.avst.authorize.common.utils.sq;

import com.avst.authorize.common.utils.*;
import com.avst.authorize.common.utils.properties.PropertiesListenerConfig;
import com.wb.deencode.DeCodeUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;

/**
 * 客户端服务器有的类
 * 解密数据
 * 加密使用时间
 */
public class AnalysisSQ {



    /*简单加密*/
    /*
     * 16进制数字字符集
     * webmac
     */
    private static String hexString="123456789ABCDEF0";
    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode_uid(String str)
    {
        //根据默认编码获取字节数组
        byte[] bytes=str.getBytes();
        StringBuilder sb=new StringBuilder(bytes.length*2);
        //将字节数组中每个字节拆解成2位16进制整数
        for(int i=0;i<bytes.length;i++)
        {
            sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
            sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
        }
        return sb.toString();
    }

    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode_uid(String bytes)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
        //将每2位16进制整数组装成一个字节
        for(int i=0;i<bytes.length();i+=2)
            baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
        return new String(baos.toByteArray());
    }


        private static String inifilename= PropertiesListenerConfig.getProperty("pro.javakeyname");//我们发出去的授权文件和运行的工程文件放在同一个目录下的
    private static String inifilename_yingcang="java.ini";//隐藏的授权文件名
    /**
     *  初始化授权文件的路径
     */
    public static String keypath= OpenUtil.getXMSoursePath()+File.separator+inifilename;

    /**
     * 隐藏记录授权运行的文件
     */
    public static String inipath= OpenUtil.getXMSoursePath()+File.separator+ inifilename_yingcang;



    /**
     * 更新客户端授权的使用时间
     * 更新天数
     * @return
     */
    public static boolean updateClientini(int day){

        try {

            String rr=ReadWriteFile.readTxtFileToStr(inipath,"utf8");

            String decode=decode_uid(rr);
            LogUtil.intoLog(AnalysisSQ.class,"updateClientini--decode:"+decode);

            String[] arr=decode.split(";");
            int useday=Integer.parseInt(arr[1]);
            if(useday==day){//时间一样的话就不需要写入授权文件
                return true;
            }
            useday=day;
            String newcode=arr[0]+";"+useday;
            LogUtil.intoLog(AnalysisSQ.class,newcode+":newcode--updateClientini："+inipath);
            ReadWriteFile.writeTxtFile(encode_uid(newcode),inipath);

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false ;
    }





    /**
     * 解析其他人的授权信息
     * @param sq
     * @return
     */
    public static SQEntity getSQEntity(String sq){

        try {

            sq=decode_uid(sq);
            String sqcode=sq.split(";")[0];
            int usetime=Integer.parseInt(sq.split(";")[1]);
            String[] sqcodearr= DeCodeUtil.decoderByDES(sqcode).split(";");
            if (sqcodearr.length < 9 ) {
                return null;
            }
            String serverType=sqcodearr[0];
            String foreverBool=sqcodearr[1];
            String startTime=sqcodearr[2];
            String cpuCode=sqcodearr[3];
            String sqDay=sqcodearr[4];
            String clientName=sqcodearr[5];
            String unitCode=sqcodearr[6];
            String sortNum=sqcodearr[7];
            String gnlist=sqcodearr[8];

            if(foreverBool.equals("true")){//永久授权不用检查使用剩余时间

            }else{
                int sqDay_int=Integer.parseInt(sqDay);
                if((sqDay_int-usetime) < 0){ //还剩多少使用时间
                    return null;
                }
            }

            SQEntity sqEntity=new SQEntity();
            sqEntity.setClientName(clientName);
            sqEntity.setCpuCode(cpuCode);
            sqEntity.setForeverBool(Boolean.valueOf(foreverBool));
            sqEntity.setServerType(serverType);
            sqEntity.setSortNum(Integer.parseInt(sortNum));
            sqEntity.setSqDay(Integer.parseInt(sqDay));
            sqEntity.setUnitCode(unitCode);
            sqEntity.setGnlist(gnlist);
            sqEntity.setStartTime(startTime);
            return sqEntity;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }






    public static void main(String[] args) {

        String rr=ReadWriteFile.readTxtFileToStr("C:\\Users\\Administrator\\Desktop\\吴斌_测试_2020-06-08 18-48-46\\shouquan\\5357565357535757414141494147564A47544555454143554141\\javatrm.ini","utf8");
        rr=DeCodeUtil.decoderByDES(rr);
        System.out.println(rr);
    }




}
