package com.avst.accredit.web.dao;

import com.avst.accredit.common.entity.User;
import com.avst.accredit.common.utils.LogUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserXML {
    /*
     *path:要写入数据的xml文件：D:\\123.xml
     *list:Users集合
     */
    public boolean writeXML(String path,List<User> list){
        boolean flag = true;
        OutputStream outputStream = null;
        XMLWriter xmlWriter = null;
        Document document = null;
        try {
            //创建document文档
            document = DocumentHelper.createDocument();
            //创建根节点
            Element rootElem = DocumentHelper.createElement("users");
            //将list里的值循环写入Element中
            for(int i=0;i<list.size();i++){
                Element userElem = DocumentHelper.createElement("user");
                Element nameElem = DocumentHelper.createElement("name");
                Element passElem = DocumentHelper.createElement("password");
                nameElem.addText(list.get(i).getLoginaccount());
                passElem.addText(list.get(i).getPassword());
                userElem.addAttribute("id",String.valueOf(i+1));
                userElem.add(nameElem);
                userElem.add(passElem);
                rootElem.add(userElem);
            }
            document.add(rootElem);


            // 5、设置生成xml的格式
            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
            // 设置编码格式
            outputFormat.setEncoding("UTF-8");
            outputStream = new FileOutputStream(path);
            xmlWriter = new XMLWriter(outputStream,outputFormat);
            xmlWriter.write(document);
        } catch (IOException e){
            LogUtil.intoLog(4, UserXML.class, "io Exception:" + e);
            return false;
        } catch (Exception e){
            LogUtil.intoLog(4, UserXML.class, "Exception:" + e);
            return false;
        } finally {
            if (null != xmlWriter) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /*
     *filePath:xml文件路径
     */
    public List<User> readXml(String filePath)  {
        List<User> list =  new ArrayList<User>();
        InputStream in = null;
        // 解析xml文档内容
        try {
            SAXReader reader = new SAXReader();
            in = new FileInputStream(new File(filePath));
            Document doc = reader.read(in);
            //获取根节点
            Element root = doc.getRootElement();
            List<Element> usersElem = root.elements();
//            System.out.println("用户数量："+usersElem.size());
            for (Element userElem : usersElem) {
                //获取user的index属性值
                String index = userElem.attribute("id").getValue();
//                System.out.println("用户下标："+index);

                List<Element> textElem = userElem.elements();
                User user = new User();
                user.setId(Integer.parseInt(index));
                user.setLoginaccount(textElem.get(0).getText());        //获取name文本值
                user.setPassword(textElem.get(1).getText());    //获取password文本值
                list.add(user);
            }
        } catch (Exception e) {
            System.out.println("error: "+ e);
            return null;
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}