package com.avst.accredit;

import java.io.File;
import java.io.FileOutputStream;

import com.avst.accredit.common.entity.User;
import com.avst.accredit.common.utils.Page;
import com.avst.accredit.web.dao.UserXML;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccreditApplicationTests {

    @Test
    public void contextLoads() {

//        Long start = System.currentTimeMillis();
//        createXml();
//        System.out.println("运行时间："+ (System.currentTimeMillis() - start));


        UserXML userXML = new UserXML();
//
//        List<User> list = new ArrayList<User>();
//
//        for (int i = 0; i < 5; i++) {
//            User user = new User();
//            user.setLoginaccount(i + "");
//            user.setPassword(i + "密码");
//            list.add(user);
//        }
//
//        userXML.writeXML("D:\\123.xml",list);

        List<User> users = userXML.readXml("D:\\123.xml");
        System.out.println(users);

        Page page = new Page();


    }



    /**
     * 生成xml方法
     */
    public static void createXml(){
        try {
            // 1、创建document对象
            Document document = DocumentHelper.createDocument();
            // 2、创建根节点rss
            Element rss = document.addElement("rss");
            // 3、向rss节点添加version属性
            rss.addAttribute("version", "1.0");
            // 4、生成子节点及子节点内容
            Element channel = rss.addElement("channel");
            Element title = channel.addElement("title");
            title.setText("国内最新新闻");
            // 5、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");


            // 6、生成xml文件
            File file = new File("rss.xml");
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("生成rss.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成rss.xml失败");
        }
    }



}
