package com.avst.authorize.common.utils.sq;


import com.avst.authorize.common.entity.SQEntityPlus;
import com.avst.authorize.common.utils.DateUtil;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.ReadWriteFile;
import com.wb.deencode.EncodeUtil;

import java.io.File;

/**
 *  创建授权文件
 *  授权的UnitCode一定是有规则的，例如：最上面的服务器是hb,下一级hb_wh,hb_wh_hk,最下级的客户端服务器也是hb_wh_hk；
 *   当前的节点服务器和该节点的下级服务器（客户端服务器）UnitCode一致，只是SortNum不同，节点是0，其他自动在上一个数值上加1
 */
public class CreateSQ {

    private static String javakeyname="javatrm.ini";

    /**
     * 只有总站才会有的，本地运行都可以，不能部署到服务上去的类
     * 授权文件生成
     * @param sqEntity
     * @param basepath
     * @return
     */
    public static boolean deSQ(SQEntityPlus sqEntity, String basepath){

        String sqcode=sqEntity.toString();
        try {
            LogUtil.intoLog(1, CreateSQ.class, "授权创建前 sqcode:" + sqcode);
            String rr= EncodeUtil.encoderByDES(sqcode);
            LogUtil.intoLog(1, CreateSQ.class, "授权创建后 rr:" + rr);

            String path=basepath+"\\"+ javakeyname;
            int count = 5;
            for (int i = 0; i < count; i++) { //进行5次写出，确保文件一定写到文件中
                File file = new File(path);
                if(!file.exists()){
                    boolean b = ReadWriteFile.writeTxtFile(rr, path);
                    if(!b){
                        LogUtil.intoLog(4, CreateSQ.class, "授权文件写出失败:" + path);
                        if(count <= i){
                            return false;
                        }
                    }else{
                        LogUtil.intoLog(1, CreateSQ.class, "把授权写到授权文件中保存成功:" + path);
                    }
                }else{
                    break;
                }
            }

            return true;
        }catch (Exception e){
            LogUtil.intoLog(4, CreateSQ.class, sqcode + " 授权失败。。。");
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {

        SQEntityPlus sqEntity= new SQEntityPlus();
        //授权的UnitCode一定是有规则的，例如：最上面的服务器是hb,下一级hb_wh,hb_wh_hk,最下级的客户端服务器也是hb_wh_hk；
        // 当前的节点服务器和该节点的下级服务器（客户端服务器）UnitCode一致，只是SortNum不同，节点是0，其他自动在上一个数值上加1
        sqEntity.setUnitCode("avst");
        sqEntity.setSqDay(100);
        sqEntity.setSortNum(2);
        sqEntity.setServerType("police");
        sqEntity.setForeverBool(false);//是否永久授权
        sqEntity.setClientName("人民公安审讯系统");
        sqEntity.setCpuCode("535756535753575741414144414754444243454344444943");

        sqEntity.setGnlist("record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:aaaa}"); //通过集合转成字符串，以|的方式分割
        sqEntity.setStartTime(DateUtil.getDateAndMinute());

        LogUtil.intoLog(CreateSQ.class,deSQ(sqEntity,"D:\\trmshouquan"));//最后生成


    }

}
