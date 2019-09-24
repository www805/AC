package com.avst.accredit.web.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import com.avst.accredit.common.utils.DateUtil;
import com.avst.accredit.common.utils.OpenUtil;
import com.avst.accredit.common.utils.sq.SQEntity;
import org.dom4j.io.OutputFormat;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

public class SQEntityRoom_R_W_XML {

	public static List<SQEntity> readXml(String xmlurl) {
		try {

			List<SQEntity> xmls=new ArrayList<SQEntity>();

			//判断如果xml不存在就返回
			File file = new File(xmlurl);
			if (!file.exists()) {
				return xmls;
			}

			// 1. 创建org.jdom.input.SAXBuilder对象
			SAXBuilder saxBuilder = new SAXBuilder();
			// 2. 创建一个输入流, 用来加载xml文件
			InputStream ins = new FileInputStream(xmlurl);
			org.jdom.Document document = saxBuilder.build(ins);

			// 3. 获取根节点
			Element rootElement = document.getRootElement();


			// 4. 获取根节点下的子节点
			List<Element> lists = rootElement.getChildren();
			
			if(null==lists||lists.size() ==0){
				return null;
			}
			for (Element people : lists) {
				
				SQEntity xml=new SQEntity();
				// A.获取所有的属性
				// System.out.println("====属性值："+people.getAttributeValue("id"));
//				List<Attribute> attributeList = people.getAttributes();
//				for (Attribute element : attributeList) {
//					xml.setCourtroomcode(element.getValue());
//				}

				// B. 遍历people节点下所有的节点名和节点值
				List<Element> childList = people.getChildren();
				int i=0;
				for (Element object : childList) {
					if(i==0){
						xml.setClientName(object.getValue());
					}else if(i==1){
						xml.setUnitCode(object.getValue());
					}else if(i==2){
						xml.setSqDay(Integer.parseInt(object.getValue()));
					}else if(i==3){
						xml.setSortNum(Integer.parseInt(object.getValue()));
					}else if(i==4){
						xml.setServerType(object.getValue());
					}else if(i==5){
						String value = object.getValue();
						xml.setForeverBool("true".equalsIgnoreCase(value) ? true : false);
					}else if(i==6){
						xml.setCpuCode(object.getValue());
					}else if(i==7){
						xml.setGnlist(object.getValue());
					}else if(i==8){
						xml.setStartTime(object.getValue());
					}else if(i==9){
						xml.setSsid(object.getValue());
					}else if(i==10){
						xml.setState(object.getValue());
					}
					i++;
				}
				//状态是1的就加入集合中
				if("1".equals(xml.getState())){
					xmls.add(xml);
				}
			}
			return xmls;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static SQEntity readXml_equipment(String xml_req) {
		try {
//			StringReader sr = new StringReader(xml_req);
//			InputSource is = new InputSource(sr);
			InputStream is = new FileInputStream(xml_req);
			Document document = (new SAXBuilder()).build(is);

			// 3. 获取根节点
			Element rootElement = document.getRootElement();

			// 4. 获取根节点下的子节点
			List<Element> lists = rootElement.getChildren();
			
			if(null==lists||lists.size() ==0){
				return null;
			}
			SQEntity xml=new SQEntity();
			int i=0;
			for (Element et : lists) {
				String str=et.getText();
				if(i==0){
					xml.setClientName(str);
				}else if(i==1){
					xml.setUnitCode(str);
				}else if(i==2){
					xml.setSqDay(Integer.parseInt(str));
				}else if(i==3){
					xml.setSortNum(Integer.parseInt(str));
				}else if(i==4){
					xml.setServerType(str);
				}else if(i==5){
					xml.setForeverBool(str == "true" ? true : false);
				}else if(i==6){
					xml.setCpuCode(str);
				}else if(i==7){
					xml.setGnlist(str);
				}else if(i==8){
					xml.setStartTime(str);
				}else if(i==9){
					xml.setSsid(str);
				}else if(i==10){
					xml.setState(str);
				}
				i++;
			}
			return xml;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void writeXml_courtRoom(String xmlurl,SQEntity sqEntity) {
		
		try {
		
			OpenUtil.createpath_file(xmlurl);
			// 1. 创建org.jdom.input.SAXBuilder对象
			SAXBuilder saxBuilder = new SAXBuilder();
			// 2. 创建一个输入流, 用来加载xml文件
			InputStream ins = new FileInputStream(xmlurl);
			int i=ins.read();
			if(i<=0){
				List<SQEntity> SQEntity_List=new ArrayList<SQEntity>();
				SQEntity_List.add(sqEntity);
				writeXml_courtRoomList(xmlurl,SQEntity_List);
			}else{
				try {
					org.jdom.Document document = saxBuilder.build(ins);
					
					// 3. 获取根节点
					Element root = document.getRootElement();
					//生成一个文档
					Document Doc = root.getDocument(); 
					
					
					Element element = new Element("sq");
					//设置属性名和属性值
//					element.setAttribute("courtroomcode", SQEntity.getCourtroomcode());

					element.addContent(new Element("clientName").setText(sqEntity.getClientName()));
					element.addContent(new Element("unitCode").setText(sqEntity.getUnitCode()));
					element.addContent(new Element("sqDay").setText(sqEntity.getSqDay() + ""));
					element.addContent(new Element("sortNum").setText(sqEntity.getSortNum() + ""));
					element.addContent(new Element("serverType").setText(sqEntity.getServerType()));
					element.addContent(new Element("foreverBool").setText(sqEntity.isForeverBool() + ""));
					element.addContent(new Element("cpuCode()").setText(sqEntity.getCpuCode()));
					element.addContent(new Element("gnlist").setText(sqEntity.getGnlist()));
					element.addContent(new Element("startTime").setText(sqEntity.getStartTime()));
					element.addContent(new Element("ssid").setText(sqEntity.getSsid()));
					element.addContent(new Element("state").setText(sqEntity.getState()));

					//将已经设置好值的elements赋给root
					root.addContent(element);  
					
					//定义一个用于输出xml文档的类
					XMLOutputter XMLOut = new XMLOutputter();



					// 设置生成xml的格式
					Format format = Format.getPrettyFormat();

					// 设置编码格式
					format.setEncoding("UTF-8");

					XMLOut.setFormat(format);

					
					//将生成的xml文档Doc输出到文件
					XMLOut.output(Doc, new FileOutputStream(xmlurl));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}catch (JDOMException e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeXml_courtRoomList(String xmlurl,List<SQEntity> SQEntity_List) {
		//定义一个root作为xml文档的根元素
		Element root = new Element("sqs");
		//生成一个文档
		Document Doc = new Document(root);   
		for (int j = 0; j < SQEntity_List.size(); j++) { 
			
			SQEntity SQEntity=SQEntity_List.get(j);
			
			Element elements = new Element("sq");
			//设置属性名和属性值
//			elements.setAttribute("id", j + "");

			elements.addContent(new Element("clientName").setText(SQEntity.getClientName()));
			elements.addContent(new Element("unitCode").setText(SQEntity.getUnitCode()));
			elements.addContent(new Element("sqDay").setText(SQEntity.getSqDay() + ""));
			elements.addContent(new Element("sortNum").setText(SQEntity.getSortNum() + ""));
			elements.addContent(new Element("serverType").setText(SQEntity.getServerType()));
			elements.addContent(new Element("foreverBool").setText(SQEntity.isForeverBool() + ""));
			elements.addContent(new Element("cpuCode").setText(SQEntity.getCpuCode()));
			elements.addContent(new Element("gnlist").setText(SQEntity.getGnlist()));
			elements.addContent(new Element("startTime").setText(SQEntity.getStartTime()));
			elements.addContent(new Element("ssid").setText(SQEntity.getSsid()));
			elements.addContent(new Element("state").setText(SQEntity.getState()));
			//将已经设置好值的elements赋给root
			root.addContent(elements);  
		} 
		//定义一个用于输出xml文档的类
		XMLOutputter XMLOut = new XMLOutputter();  

		try {

			// 设置生成xml的格式
			Format format = Format.getPrettyFormat();

			// 设置编码格式
			format.setEncoding("UTF-8");
			
			OpenUtil.createpath_file(xmlurl);

			XMLOut.setFormat(format);
			
			//将生成的xml文档Doc输出到文件
			XMLOut.output(Doc, new FileOutputStream(xmlurl));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
//		List<SQEntity> SQEntity_List= SQEntity_List=new ArrayList<SQEntity>();
//		String courtcode="sgzy_gd";
//		for(int i=0;i<5;i++){
//			SQEntity sqEntity = new SQEntity();
//			sqEntity.setUnitCode("avst");
//			sqEntity.setSqDay(100);
//			sqEntity.setSortNum(2);
//			sqEntity.setServerType("police");
//			sqEntity.setForeverBool(false);//是否永久授权
//			sqEntity.setClientName("人民公安审讯系统");
//			sqEntity.setCpuCode("5357565357535757414141444147554542414248444148464346");
//
//			sqEntity.setGnlist("record|asr|fd|ph"); //通过集合转成字符串，以|的方式分割
//			sqEntity.setStartTime(DateUtil.getDateAndMinute());
//			SQEntity_List.add(sqEntity);
//		}
//
//		writeXml_courtRoomList("D:/courtrooms.xml",SQEntity_List);
		
//		SQEntity sqEntity=new SQEntity();
//		sqEntity.setCourtroomcode(OpenUtil.getUUID_32());
//		sqEntity.setCourtroomname("法庭"+4);
//		sqEntity.setPushname("bl"+4);
//		
//		writeXml_courtRoom("D:/courtrooms.xml",sqEntity);


		List<SQEntity> sqEntities = readXml("D:/courtrooms.xml");
//		System.out.println(sqEntities);

		for (SQEntity sqEntity : sqEntities) {
			System.out.println(sqEntity);
		}

//		SQEntity sqEntity1 = readXml_equipment("D:/courtrooms.xml");
//		System.out.println(sqEntity1);

		System.out.println(1234);
		
		
	}
}
