package com.avst.authorize.common.utils.sq;


import com.avst.authorize.common.utils.DateUtil;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.ReadWriteFile;
import com.wb.deencode.EncodeUtil;

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
    public static boolean deSQ(SQEntity sqEntity,String basepath){

        try {
            String sqcode=sqEntity.toString();
            LogUtil.intoLog(CreateSQ.class,"授权创建前 sqcode:"+sqcode);
            String rr= EncodeUtil.encoderByDES(sqcode);
            LogUtil.intoLog(CreateSQ.class,"授权创建后 rr:"+rr);

            String path=basepath+"\\"+ javakeyname;
            ReadWriteFile.writeTxtFile(rr,path);

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {

        SQEntity sqEntity= new SQEntity();
        //授权的UnitCode一定是有规则的，例如：最上面的服务器是hb,下一级hb_wh,hb_wh_hk,最下级的客户端服务器也是hb_wh_hk；
        // 当前的节点服务器和该节点的下级服务器（客户端服务器）UnitCode一致，只是SortNum不同，节点是0，其他自动在上一个数值上加1
        sqEntity.setUnitCode("avst");
        sqEntity.setSqDay(100);
        sqEntity.setSortNum(2);
        sqEntity.setServerType("police");
        sqEntity.setForeverBool(false);//是否永久授权
        sqEntity.setClientName("人民公安审讯系统");
        sqEntity.setCpuCode("5357565357535757414141444147554542414248444148464346");

        sqEntity.setGnlist("record|asr|fd|ph"); //通过集合转成字符串，以|的方式分割
        sqEntity.setStartTime(DateUtil.getDateAndMinute());

        LogUtil.intoLog(CreateSQ.class,deSQ(sqEntity,"E:\\trmshouquan"));//最后生成


    }

}