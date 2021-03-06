package com.avst.authorize.common.utils.sq;
import com.avst.authorize.common.utils.LogUtil;
import com.avst.authorize.common.utils.ReadWriteFile;
import org.apache.commons.lang3.StringUtils;

import java.io.*;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 授权已经到了第三阶段 使用CPU序列号+C盘序列号
 */
public class NetTool{
  
public static void main( String[] args){  
 
	try {

//		getSQCode();

		System.out.println(getSQCode());

	} catch (Exception e) {
		
		e.printStackTrace();
	}
}

	public static String getSQCode(){
		String os = getOsName();
		System.out.println(os+":os");
		String mac=null;
		if (os.startsWith("Win")) {
			mac= getSQCode_win();
		} else if (os.startsWith("Linux")) {
			mac= getSQCode_linux();
		}else{
			System.out.println("getSQ is error ，os："+os);
			return null;
		}
		System.out.println(mac+":os trmcode");
		return mac;
	}

	/**
	 * 获取本地设备的授权码
	 * 现阶段用CPU序列号+硬盘序列号加密一层
	 * @return
	 */
	public static String getSQCode_win(){
		String cpuCode=getCPUCode_win();
//		String ypCode=getHdSerialInfo();
//		String ypCode=getSerialNumber();
		String ypCode=getDiskID(false);//第六代授权

		String sqcode="";
		if(null!=cpuCode&&!cpuCode.trim().equals("")){
			sqcode=cpuCode;
		}
		if(null!=ypCode&&!ypCode.trim().equals("")){
			sqcode+=ypCode;
		}
		return AnalysisSQ.encode_uid(sqcode);
	}

	/**
	 * 获取本地设备的授权码
	 * 现阶段用CPU序列号+第一块磁盘序列号
	 * @return
	 */
	public static String getSQCode_linux(){
		String cpuCode=getCPUID_linux();

		String ypCode=getIdentifierByLinux();
		String sqcode="";
		if(null!=cpuCode&&!cpuCode.trim().equals("")){
			sqcode=cpuCode.trim();
		}
		if(null!=ypCode&&!ypCode.trim().equals("")){
			sqcode+=ypCode.trim();
		}
		return AnalysisSQ.encode_uid(sqcode);
	}

	/**
	 * 获取Linux第一块磁盘的序列号
	 * @return
	 */
	private static String getIdentifierByLinux(){
		String[] cmd = {"fdisk", "-l"};
		String result=null;
		BufferedReader bufferedReader = null;
		Process p = null;
		InputStream in2=null;
		InputStream in=null;
		OutputStream os = null;
		try {
			p = Runtime.getRuntime().exec(cmd);// 管道
			os=p.getOutputStream();
			in=p.getInputStream();
			in2=p.getErrorStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			printMessage(in2);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.indexOf("identifier:") > -1){

					String str2 = line.split("identifier:")[1].trim();
					System.out.println("Identifier is: "+str2);
					result=str2;
					break;
				}
			}

			if(null!=result&&result.length() > 0){
				result=result.replaceAll("-", "");
			}

			int exitvalue=p.waitFor();
			if(exitvalue!=0){
				throw new Exception("exitvalue is not 0, 说明代码有错");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			close(os, in, in2, bufferedReader, p);
		}
		return result.trim();


	}




  
//取得LOCALHOST的IP地址  
public static String getMyIP() {
	InetAddress myIPaddress=null;
try { 
	myIPaddress=InetAddress.getLocalHost();
	}
catch (Exception e) {}
return (myIPaddress.getHostAddress());
}  
//取得 www.baidu.com 的IP地址  
public static String getServerIP(String url){ 
	InetAddress myServer=null; 
	String hostname=null;
try {
	myServer=InetAddress.getByName(url);
	hostname=myServer.getHostName();
}  
catch (UnknownHostException e) {}  
return (hostname);  
}  




private static String getLocalMac_win() throws SocketException {


	StringBuffer sb=new StringBuffer("");
	try {
		//获取网卡，获取地址
		InetAddress ia = InetAddress.getLocalHost();

		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

//		LogUtil.intoLog(NetTool.class,"mac数组长度："+mac.length);


		for(int i=0; i<mac.length; i++) {

			if(i!=0) {

				sb.append("");

			}

			//字节转换为整数

			int temp = mac[i]&0xff;

			String str = Integer.toHexString(temp);

//			LogUtil.intoLog(NetTool.class,"每8位:"+str);

			if(str.length()==1) {

				sb.append("0"+str);

			}else {

				sb.append(str);

			}

		}
//		LogUtil.intoLog(NetTool.class,"本机MAC地址:"+sb.toString().toUpperCase());
	} catch (UnknownHostException e) {
		e.printStackTrace();
	}

	return sb.toString();
}



/**
 * Return Opertaion System Name;
 * 
 * @return os name.
 */
public static String getOsName() {
    String os = "";
    os = System.getProperty("os.name");
    return os;
}

	/**
	 * 1是win
	 * 2是Linux
	 * 3其他
	 * @return
	 */
	public static int osType() {
		String address = "";
		String os = getOsName();
		if (os.startsWith("Win")) {
			return 1;
		} else if (os.startsWith("Linux")) {
			return 2;
		}
		return 2;
	}

/**
 * Returns the MAC address of the computer.
 * 
 * @return the MAC address
 */
public static String getLocalMac() {
    String address = "";
    String os = getOsName();
    if (os.startsWith("Win")) {
        try {
			address=getLocalMac_win();
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
    } else if (os.startsWith("Linux")) {
        String command = "/bin/sh -c ifconfig -a";
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("HWaddr") > 0) {
                    int index = line.indexOf("HWaddr") + "HWaddr".length();
                    address = line.substring(index);
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
        }
    }
    address = address.trim();

	address=AnalysisSQ.encode_uid(address);

    return address;
}


	/**
	 * 获取CPU序列号
	 * @return
	 */
	public static String getCPUCode(){

		String cpuCode="";
		String os = getOsName();
		if (os.startsWith("Win")) {
			try {
				cpuCode= getCPUCode_win();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (os.startsWith("Linux")) {
			cpuCode= getCPUID_linux();
		}else{
			LogUtil.intoLog(4,NetTool.class,"getCPUID is error ，os："+os);
		}
		if(null!=cpuCode&&!cpuCode.trim().equals("")){
			cpuCode=AnalysisSQ.encode_uid(cpuCode);
		}else{
			LogUtil.intoLog(4,NetTool.class,"cpuCode is null");
		}
		return cpuCode;
	}

	/**
	 * Linux系统获取CPU序列号
	 * @return
	 */
	private static String getCPUID_linux()  {
		String result = "";
		String CPU_ID_CMD = "dmidecode";
		BufferedReader bufferedReader = null;
		OutputStream os = null;
		Process p = null;
		InputStream in2=null;
		InputStream in=null;
		try {
			p = Runtime.getRuntime().exec(new String[]{ "sh", "-c", CPU_ID_CMD });// 管道
			os=p.getOutputStream();
			in=p.getInputStream();
			in2=p.getErrorStream();
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int index = -1;

			printMessage(in2);

			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("uuid");
				if (index >= 0) {// 找到了
					// 取出mac地址并去除2边空格
					result = line.substring(index + "uuid".length() + 1).trim();
					break;
				}
			}

			if(null!=result&&result.length() > 0){
				result=result.replaceAll("-", "");
			}

			int exitvalue=p.waitFor();
			if(exitvalue!=0){
				throw new Exception("exitvalue is not 0, 说明代码有错");
			}

		} catch (Exception e) {
			System.out.println("getCPUID_linux error");
			e.printStackTrace();
		}finally {
			close(os, in, in2, bufferedReader, p);
		}
		System.out.println(result+":CPUID_linux");
		return result.trim();
	}

	/**
	 * 获取win CPU序列号
	 * @return
	 */
	private static String getCPUCode_win(){

		OutputStream os = null;
		InputStream in=null;
		InputStream in2=null;
		Process process=null;
		BufferedReader br = null;
		try {
			long start = System.currentTimeMillis();
			process = Runtime.getRuntime().exec(
					new String[] { "wmic", "cpu", "get", "ProcessorId" });
			os=process.getOutputStream();
			os.close();
			in=process.getInputStream();
			in2=process.getErrorStream();
			br=new BufferedReader(new  InputStreamReader(in));
			String line2 = null ;
			while ((line2 = br.readLine()) !=  null ) {
				if (!line2 .trim().equals("")&&!line2 .trim().equals("ProcessorId")){
					break;
				}
			}
			printMessage(in2);
			int exitvalue=process.waitFor();
			if(exitvalue!=0){
				throw new Exception(exitvalue+"exitvalue is not 0, 说明代码有错");
			}

			System.out.println( "::::::: " + line2);
			return line2.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(os, in, in2, br, process);
		}

		return null;

	}

	public static String getHdSerialInfo() {

		String line = "";
		String HdSerial = "";//定义变量 硬盘序列号
		BufferedReader bufferedReader = null;
		Process p = null;
		InputStream in2=null;
		InputStream in=null;
		InputStreamReader isr=null;
		OutputStream os = null;
		try {

			p = Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c dir c:");//获取命令行参数
			os=p.getOutputStream();
			os.close();
			in=p.getInputStream();
			isr=new InputStreamReader(in,"gbk");
			in2=p.getErrorStream();
			bufferedReader = new BufferedReader(isr);
			printMessage(in2);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.indexOf("卷的序列号是 ") != -1) {  //读取参数并获取硬盘序列号

					HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
					break;
				}
			}

			int exitvalue=p.waitFor();
			p.destroy();
			if(exitvalue!=0){
				throw new Exception(exitvalue+"exitvalue is not 0, 说明代码有错");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(null!=isr){
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			close(os, in, in2, bufferedReader, p);
		}

		return HdSerial;
	}

	/**
	 * C盘序列号
	 * @return
	 */
	public static String getSerialNumber() {

		String drive="C";//只找C盘
		String result = "";
		FileWriter fw=null;
		BufferedReader bufferedReader = null;
		Process p = null;
		InputStream in2=null;
		InputStream in=null;
		InputStreamReader isr=null;
		OutputStream os = null;
		try {
			File file = File.createTempFile("realhowto",".vbs");
			file.deleteOnExit();
			fw = new java.io.FileWriter(file);
			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+"Set colDrives = objFSO.Drives\n"
					+"Set objDrive = colDrives.item(\"" + drive + "\")\n"
					+"Wscript.Echo objDrive.SerialNumber";  // see note
			fw.write(vbs);
			fw.close();
			p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());

			os=p.getOutputStream();
			os.close();
			in=p.getInputStream();
			isr=new InputStreamReader(in,"gbk");
			in2=p.getErrorStream();
			bufferedReader = new BufferedReader(isr);

			printMessage(in2);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				result += line;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(null!=isr){
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null!=fw){
					fw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			close(os, in, in2, bufferedReader, p);
		}
		return result.trim();
	}

	/**
	 * 获取当前主机的第一块硬盘的ID
	 * win专用
	 * @return
	 */
	public static String getDisk0ID() {

		String result = "";
		FileWriter fw=null;
		BufferedReader bufferedReader = null;
		Process p = null;
		InputStream in2=null;
		InputStream in=null;
		InputStreamReader isr=null;
		OutputStream os = null;

		try {

			File file = File.createTempFile("diskpart",".bat");
			file.deleteOnExit();
			File file2 = File.createTempFile("javaw",".ini");
			file2.deleteOnExit();
			System.out.println(file2.getPath());
			File file3 = File.createTempFile("diskpart",".script");
			file3.deleteOnExit();
			File file4 = File.createTempFile("run",".bat");
			file4.deleteOnExit();

            File file5 = File.createTempFile("ceshi",".vbs");
            file5.deleteOnExit();
			//生成磁盘信息
			try {

				fw = new FileWriter(file3);
				String script1 = "select disk 0";
				script1+="\n detail disk \n exit &";
				fw.write(script1);
				fw.close();

				fw = new FileWriter(file);
				script1 = "%1 mshta vbscript:CreateObject(\"Shell.Application\").ShellExecute(\"cmd.exe\",\"/c %~s0 ::\",\"\",\"runas\",0)(window.close)&&exit cd /d \"%~dp0\"";
				script1+="\n diskpart /s   "+file3.getPath()+" >"+file2.getPath()+ "\n exit &";
				fw.write(script1);
				fw.close();

				p = Runtime.getRuntime().exec(file.getPath());

				os=p.getOutputStream();
				os.close();
				in=p.getInputStream();
				isr=new InputStreamReader(in,"gbk");
				in2=p.getErrorStream();
				bufferedReader = new BufferedReader(isr);

				printMessage(in2);

				String line2;
				while ((line2 = bufferedReader.readLine()) != null) {
					System.out.println(line2+":line2");
				}

			}
			catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					if(null!=isr){
						isr.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(null!=fw){
						fw.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				close(os, in, in2, bufferedReader, p);
			}

			//读取磁盘数据
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			List<String> disk0list= ReadWriteFile.readTxtFileToList(file2.getPath(),"gbk");


			if(null!=disk0list&&disk0list.size() > 0){
				for(String line:disk0list){
					if(line.indexOf("磁盘") > -1&&line.indexOf("ID") > -1&&line.indexOf(":") > -1){
						//磁盘id
						String diskid=line.split(":")[1].trim();
						result+=diskid;
					}

					if(line.indexOf("路径") > -1&& line.indexOf("位置路径") < 0 &&line.indexOf(":") > -1){
						//路径
						String path=line.split(":")[1].trim();
						result+=path;
					}

					if(line.indexOf("目标") > -1 &&line.indexOf(":") > -1){
						//目标
						String path=line.split(":")[1].trim();
						result+=path;
					}
				}
			}

			file.delete();
			file2.delete();
			file3.delete();
			file4.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.trim();
	}

private static String diskid=null;

	public static String getDiskID(boolean bool){

		if(StringUtils.isEmpty(diskid)||bool){
			diskid=getDisk0ID();

			LogUtil.intoLog(1,NetTool.class,"重置一次diskid："+diskid);
		}
		return diskid;
	}

	private static void printMessage(final InputStream input) {
		new Thread(new Runnable() {
       	public void run() {
        	Reader reader = new InputStreamReader(input);
			BufferedReader bf = new BufferedReader(reader);
        	String line = null;
        	  try {
					while((line=bf.readLine())!=null) {
						System.out.println(line);
					}
         		} catch (Exception e) {
            		e.printStackTrace();
         	 	}finally {
				  try {
					  if(null!=bf){
						bf.close();
					}
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
				  try {
					  if(null!=reader){
						  reader.close();
					  }
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
			  }
        	}
    	}).start();
	}

	/**
	 * 执行cmd命令
	 * @param cmd
	 * @return
	 */
	public static String executeCMD(String cmd){
		OutputStream os = null;
		InputStream in=null;
		InputStream in2=null;
		Process process=null;
		try {
			long start = System.currentTimeMillis();
			process = Runtime.getRuntime().exec(cmd);
			os=process.getOutputStream();
			in=process.getInputStream();
			in2=process.getErrorStream();

			printMessage(in2);

			int exitvalue=process.waitFor();
			if(exitvalue!=0){
				throw new Exception("exitvalue is not 0, 说明代码有错");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {

			close(os, in, in2, null, process);
		}
		return null;

	}



	private static void close(OutputStream os,InputStream in,InputStream in2,BufferedReader br,Process process){

        try {
            Thread.sleep(1000);//为了printMessage不出现Stream closed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
			if(null!=br){
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if(null!=os){
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if(null!=process){
				process.destroy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if(null!=in){
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}  