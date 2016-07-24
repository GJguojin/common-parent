package com.gj.test.base.util;

/**
 * <p>
 * Title:文件读写操作类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: xwtec.com
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

import org.apache.log4j.Logger;

import java.io.*;
import java.util.StringTokenizer;

public class FileUtil {
	private static final Logger log = Logger.getLogger( FileUtil.class );
	private String message;

	public FileUtil() {
	}

	// 读文件
	public String ReadFile( String Path ) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream( Path );
			InputStreamReader inputStreamReader = new InputStreamReader( fileInputStream, "UTF-8" );
			reader = new BufferedReader( inputStreamReader );
			String tempString = null;
			while( ( tempString = reader.readLine() ) != null ) {
				laststr += tempString;
			}
			reader.close();
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
			if( reader != null ) {
				try {
					reader.close();
				} catch( IOException e ) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

	/**
	 * 读取文本文件内容
	 * 
	 * @param filePathAndName 带有完整绝对路径的文件名
	 * @param encoding 文本文件打开的编码方式
	 * @return 返回文本文件的内容
	 */
	public String readTxt( String filePathAndName, String encoding ) throws IOException {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer( "" );
		String st = "";
		FileInputStream fs = new FileInputStream( filePathAndName );
		InputStreamReader isr;
		if( encoding.equals( "" ) ) {
			isr = new InputStreamReader( fs );
		} else {
			isr = new InputStreamReader( fs, encoding );
		}
		BufferedReader br = new BufferedReader( isr );

		String data = "";
		while( ( data = br.readLine() ) != null ) {
			str.append( data + " " );
		}
		br.close();
		st = str.toString();
		return st;
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath 目录
	 * @return 返回目录创建后的路径
	 */
	public String createFolder( String folderPath ) throws Exception {
		String txt = folderPath;
		File myFilePath = new File( txt );
		txt = folderPath;
		if( !myFilePath.exists() ) {
			myFilePath.mkdir();
		}
		return txt;
	}

	/**
	 * 多级目录创建
	 *
	 * @param folderPath 准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths 无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfac
	 */
	public String createFolders( String folderPath, String paths ) throws Exception {
		String txts = folderPath;

		String txt;
		txts = folderPath;
		StringTokenizer st = new StringTokenizer( paths, "|" );
		for( int i = 0; st.hasMoreTokens(); i++ ) {
			txt = st.nextToken().trim();
			if( txts.lastIndexOf( "/" ) != -1 ) {
				txts = createFolder( txts + txt );
			} else {
				txts = createFolder( txts + txt + "/" );
			}
		}
		return txts;
	}

	/**
	 * 新建文件
	 *
	 * @param filePathAndName 文本文件完整绝对路径及文件名
	 * @param fileContent 文本文件内容
	 * @return
	 */
	public void createFile( String filePathAndName, String fileContent ) throws Exception {
		String filePath = filePathAndName;
		filePath = filePath.toString();
		File myFilePath = new File( filePath );
		if( !myFilePath.exists() ) {
			myFilePath.createNewFile();
		}
		FileWriter resultFile = new FileWriter( myFilePath );
		PrintWriter myFile = new PrintWriter( resultFile );
		String strContent = fileContent;
		myFile.println( strContent );
		myFile.close();
		resultFile.close();
	}

	// /**
	// * 有编码方式的文件创建
	// * @param filePathAndName 文本文件完整绝对路径及文件名
	// * @param fileContent 文本文件内容
	// * @param encoding 编码方式 例如 GBK 或者 UTF-8
	// * @return
	// */
	// public void createFile(String filePathAndName, String fileContent, String
	// encoding) {
	//

	// String filePath = filePathAndName;
	// filePath = filePath.toString();
	// File myFilePath = new File(filePath);
	// if (!myFilePath.exists()) {
	// myFilePath.createNewFile();
	// }
	// PrintWriter myFile =new PrintWriter(myFilePath,encoding);
	// String strContent = fileContent;
	// myFile.println(strContent);
	// myFile.close();

	// }

	/**
	 * 删除文件
	 *
	 * @param filePathAndName 文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public boolean delFile( String filePathAndName ) throws Exception {
		boolean bea = false;
		String filePath = filePathAndName;
		File myDelFile = new File( filePath );
		if( myDelFile.exists() ) {
			myDelFile.delete();
			bea = true;
		} else {
			bea = false;
			message = ( filePathAndName + "删除文件操作出错" );
		}
		return bea;
	}

	/**
	 * 删除文件夹
	 *
	 * @param folderPath 文件夹完整绝对路径
	 * @return
	 */
	public void delFolder( String folderPath ) {

		delAllFile( folderPath ); // 删除完里面所有内容
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File( filePath );
		myFilePath.delete(); // 删除空文件夹

	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path 文件夹完整绝对路径
	 * @return
	 * @return
	 */
	public boolean delAllFile( String path ) {
		boolean bea = false;
		File file = new File( path );
		if( !file.exists() ) {
			return bea;
		}
		if( !file.isDirectory() ) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for( int i = 0; i < tempList.length; i++ ) {
			if( path.endsWith( File.separator ) ) {
				temp = new File( path + tempList[i] );
			} else {
				temp = new File( path + File.separator + tempList[i] );
			}
			if( temp.isFile() ) {
				temp.delete();
			}
			if( temp.isDirectory() ) {
				delAllFile( path + "/" + tempList[i] ); // 先删除文件夹里面的文件
				delFolder( path + "/" + tempList[i] ); // 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile 准备复制的文件源
	 * @param newPathFile 拷贝到新绝对路径带文件名
	 * @return
	 */
	public void copyFile( String oldPathFile, String newPathFile ) throws Exception {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File( oldPathFile );
		if( oldfile.exists() ) { // 文件存在时
			InputStream inStream = new FileInputStream( oldPathFile ); // 读入原文件
			FileOutputStream fs = new FileOutputStream( newPathFile );
			byte[] buffer = new byte[1444];
			while( ( byteread = inStream.read( buffer ) ) != -1 ) {
				bytesum += byteread; // 字节数 文件大小
				// log.info(bytesum);
				fs.write( buffer, 0, byteread );
			}
			inStream.close();
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath 准备拷贝的目录
	 * @param newPath 指定绝对路径的新目录
	 * @return
	 */
	public void copyFolder( String oldPath, String newPath ) throws Exception {
		new File( newPath ).mkdirs(); // 如果文件夹不存在 则建立新文件夹
		File a = new File( oldPath );
		String[] file = a.list();
		File temp = null;
		for( int i = 0; i < file.length; i++ ) {
			if( oldPath.endsWith( File.separator ) ) {
				temp = new File( oldPath + file[i] );
			} else {
				temp = new File( oldPath + File.separator + file[i] );
			}
			if( temp.isFile() ) {
				FileInputStream input = new FileInputStream( temp );
				FileOutputStream output = new FileOutputStream( newPath + "/" + ( temp.getName() ).toString() );
				byte[] b = new byte[1024 * 5];
				int len;
				while( ( len = input.read( b ) ) != -1 ) {
					output.write( b, 0, len );
				}
				output.flush();
				output.close();
				input.close();
			}
			if( temp.isDirectory() ) { // 如果是子文件夹
				copyFolder( oldPath + "/" + file[i], newPath + "/" + file[i] );
			}
		}

	}

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFile( String oldPath, String newPath ) throws Exception {
		copyFile( oldPath, newPath );
		delFile( oldPath );
	}

	/**
	 * 移动目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public void moveFolder( String oldPath, String newPath ) throws Exception {
		copyFolder( oldPath, newPath );
		delFolder( oldPath );
	}

	public String getMessage() {
		return this.message;
	}

	/**
	 * 测试过程
	 */
	public static void main( String[] args ) {
		// /**
		// * 列出所有的System属性返回值
		// */
		// java.util.Properties pp = System.getProperties();
		// java.util.Enumeration en = pp.propertyNames();
		// while(en.hasMoreElements())
		// {
		// String temp=(String)en.nextElement();
		// log.info(temp+"="+pp.getProperty(temp));
		// }
		StringBuffer sb = new StringBuffer();
		sb.append( "<?xml version=\"1.0\" encoding=\"gbk\"?> \n" );
		sb.append( "<IfInfo> \n" );
		sb.append( " <toaccounting> \n" );
		sb.append( "		<toaccountingbasic Note=\"转固申请基本信息\">  \n" );
		sb.append( "			<toaccountingid Note=\"转固标识\">8</toaccountingid>  \n" );
		sb.append( "			<toassettype Note=\"转固类型\">1</toassettype>  \n" );
		sb.append( "			<company Note=\"核算单位\"><value></value><name></name></company>  \n" );
		sb.append( "			<itemid Note=\"项目标识\">36871</itemid>  \n" );
		sb.append( "			<itemcode Note=\"项目编号\">省电改02-06-003</itemcode>  \n" );
		sb.append( "			<applymanname Note=\"申请人姓名\">欣网视讯</applymanname>  \n" );
		sb.append( "			<applydate Note=\"申请时间\">20060321</applydate>  \n" );
		sb.append( "			<temporaryflag Note=\"资产暂估标志（1是0否）\">1</temporaryflag>  \n" );
		sb.append( "		</toaccountingbasic>  \n" );
		sb.append( "		<asset Note=\"资产明细\">  \n" );
		sb.append( "			<assetid Note=\"资产明细标识\">4</assetid>  \n" );
		sb.append( "			<assetbatch Note=\"预转固批号\"></assetbatch>  \n" );
		sb.append( "			<assetcode Note=\"预转固序号\"></assetcode>  \n" );
		sb.append( "			<ifextend Note=\"是否扩容\">0</ifextend>  \n" );
		sb.append( "			<parentcardbatch Note=\"父卡片批号\"></parentcardbatch>  \n" );
		sb.append( "			<parentcardcode Note=\"父卡片序号\"></parentcardcode>  \n" );
		sb.append( "			<category Note=\"资产目录\">0403020151</category>  \n" );
		sb.append( "			<assetname Note=\"资产名称\">(ADSL)设备机框</assetname>  \n" );
		sb.append( "			<unitsname Note=\"计量单位\">个</unitsname>  \n" );
		sb.append( "			<countnum Note=\"数量\">1</countnum>  \n" );
		sb.append( "			<productgrouptype Note=\"型号规格程式\">MA5600</productgrouptype>  \n" );
		sb.append( "			<place Note=\"所在地点\">高新</place>  \n" );
		sb.append( "			<placecode Note=\"所在地点编码\">AJB.GXJ00</placecode>  \n" );
		sb.append( "			<buydate Note=\"购建日期\">20060314</buydate>  \n" );
		sb.append( "			<usedate Note=\"使用时间\"></usedate>  \n" );
		sb.append( "			<providertexno Note=\"生产厂商税号\"></providertexno>  \n" );
		sb.append( "			<providername Note=\"生产厂商名称\">华为技术有限公司</providername>  \n" );
		sb.append( "			<deviceprovidertexno Note=\"设备供应商税号\"></deviceprovidertexno>  \n" );
		sb.append( "			<deviceprovidername Note=\"设备供应商名称\">华为技术有限公司</deviceprovidername>  \n" );
		sb.append( "			<ascription Note=\"资产归属\"></ascription>  \n" );
		sb.append( "			<speciality Note=\"专业属性\"></speciality>  \n" );
		sb.append( "			<designno Note=\"所附设计图纸图号\"></designno>  \n" );
		sb.append( "			<contractprice Note=\"设备购买价\">42281.8</contractprice>  \n" );
		sb.append( "			<relatefee Note=\"设备采购支付相关费用\">0</relatefee>  \n" );
		sb.append( "			<!--以下是新增的-->  \n" );
		sb.append( "			<principal Note=\"责任人\"></principal>  \n" );
		sb.append( "			<userdepartment Note=\"使用部门\"></userdepartment>  \n" );
		sb.append( "			<increasereason Note=\"资产增加原因\">在建工程导入</increasereason><!--根据项目的是否是单纯购置来判断   在建工程转入或单纯购置-->  \n" );
		sb.append( "			<practicalityno Note=\"实物资产编号\"></practicalityno><!--待确定编码方式-->  \n" );
		sb.append( "			<memo Note=\"备注\"></memo>			  \n" );
		sb.append( "		</asset>  \n" );
		sb.append( "		<asset Note=\"资产明细\">  \n" );
		sb.append( "			<assetid Note=\"资产明细标识\">5</assetid>  \n" );
		sb.append( "			<assetbatch Note=\"预转固批号\"></assetbatch>  \n" );
		sb.append( "			<assetcode Note=\"预转固序号\"></assetcode>  \n" );
		sb.append( "			<ifextend Note=\"是否扩容\">0</ifextend>  \n" );
		sb.append( "			<parentcardbatch Note=\"父卡片批号\"></parentcardbatch>  \n" );
		sb.append( "			<parentcardcode Note=\"父卡片序号\"></parentcardcode>  \n" );
		sb.append( "			<category Note=\"资产目录\">0403020152</category>  \n" );
		sb.append( "			<assetname Note=\"资产名称\">(ADSL)设备板卡</assetname>  \n" );
		sb.append( "			<unitsname Note=\"计量单位\">块</unitsname>  \n" );
		sb.append( "			<countnum Note=\"数量\">15</countnum>  \n" );
		sb.append( "			<productgrouptype Note=\"型号规格程式\">MA5600（业务板、主控板）</productgrouptype>  \n" );
		sb.append( "			<place Note=\"所在地点\">高新</place>  \n" );
		sb.append( "			<placecode Note=\"所在地点编码\">AJB.GXJ00</placecode>  \n" );
		sb.append( "			<buydate Note=\"购建日期\">20060314</buydate>  \n" );
		sb.append( "			<usedate Note=\"使用时间\"></usedate>  \n" );
		sb.append( "			<providertexno Note=\"生产厂商税号\"></providertexno>  \n" );
		sb.append( "			<providername Note=\"生产厂商名称\">华为技术有限公司</providername>  \n" );
		sb.append( "			<deviceprovidertexno Note=\"设备供应商税号\"></deviceprovidertexno>  \n" );
		sb.append( "			<deviceprovidername Note=\"设备供应商名称\">华为技术有限公司</deviceprovidername>  \n" );
		sb.append( "			<ascription Note=\"资产归属\"></ascription>  \n" );
		sb.append( "			<speciality Note=\"专业属性\"></speciality>  \n" );
		sb.append( "			<designno Note=\"所附设计图纸图号\"></designno>  \n" );
		sb.append( "			<contractprice Note=\"设备购买价\">62899.2</contractprice>  \n" );
		sb.append( "			<relatefee Note=\"设备采购支付相关费用\">0</relatefee>  \n" );
		sb.append( "			<!--以下是新增的-->  \n" );
		sb.append( "			<principal Note=\"责任人\"></principal>  \n" );
		sb.append( "			<userdepartment Note=\"使用部门\"></userdepartment>  \n" );
		sb.append( "			<increasereason Note=\"资产增加原因\">在建工程导入</increasereason><!--根据项目的是否是单纯购置来判断   在建工程转入或单纯购置-->  \n" );
		sb.append( "			<practicalityno Note=\"实物资产编号\"></practicalityno><!--待确定编码方式-->  \n" );
		sb.append( "			<memo Note=\"备注\"></memo>			  \n" );
		sb.append( "		</asset>  \n" );
		sb.append( "		<asset Note=\"资产明细\">  \n" );
		sb.append( "			<assetid Note=\"资产明细标识\">6</assetid>  \n" );
		sb.append( "			<assetbatch Note=\"预转固批号\"></assetbatch>  \n" );
		sb.append( "			<assetcode Note=\"预转固序号\"></assetcode>  \n" );
		sb.append( "			<ifextend Note=\"是否扩容\">0</ifextend>  \n" );
		sb.append( "			<parentcardbatch Note=\"父卡片批号\"></parentcardbatch>  \n" );
		sb.append( "			<parentcardcode Note=\"父卡片序号\"></parentcardcode>  \n" );
		sb.append( "			<category Note=\"资产目录\">01060101</category>  \n" );
		sb.append( "			<assetname Note=\"资产名称\">光纤分配架（ODF）</assetname>  \n" );
		sb.append( "			<unitsname Note=\"计量单位\">架</unitsname>  \n" );
		sb.append( "			<countnum Note=\"数量\">1</countnum>  \n" );
		sb.append( "			<productgrouptype Note=\"型号规格程式\">一体化熔配ODF单元，世纪人GPX98型72芯子框</productgrouptype>  \n" );
		sb.append( "			<place Note=\"所在地点\">高新</place>  \n" );
		sb.append( "			<placecode Note=\"所在地点编码\">AJB.GXJ00</placecode>  \n" );
		sb.append( "			<buydate Note=\"购建日期\">20060314</buydate>  \n" );
		sb.append( "			<usedate Note=\"使用时间\"></usedate>  \n" );
		sb.append( "			<providertexno Note=\"生产厂商税号\"></providertexno>  \n" );
		sb.append( "			<providername Note=\"生产厂商名称\">深圳世纪人</providername>  \n" );
		sb.append( "			<deviceprovidertexno Note=\"设备供应商税号\"></deviceprovidertexno>  \n" );
		sb.append( "			<deviceprovidername Note=\"设备供应商名称\">深圳世纪人</deviceprovidername>  \n" );
		sb.append( "			<ascription Note=\"资产归属\"></ascription>  \n" );
		sb.append( "			<speciality Note=\"专业属性\"></speciality>  \n" );
		sb.append( "			<designno Note=\"所附设计图纸图号\"></designno>  \n" );
		sb.append( "			<contractprice Note=\"设备购买价\">1119</contractprice>  \n" );
		sb.append( "			<relatefee Note=\"设备采购支付相关费用\">0</relatefee>  \n" );
		sb.append( "			<!--以下是新增的-->  \n" );
		sb.append( "			<principal Note=\"责任人\"></principal>  \n" );
		sb.append( "			<userdepartment Note=\"使用部门\"></userdepartment>  \n" );
		sb.append( "			<increasereason Note=\"资产增加原因\">在建工程导入</increasereason><!--根据项目的是否是单纯购置来判断   在建工程转入或单纯购置-->  \n" );
		sb.append( "			<practicalityno Note=\"实物资产编号\"></practicalityno><!--待确定编码方式-->  \n" );
		sb.append( "			<memo Note=\"备注\"></memo>			  \n" );
		sb.append( "		</asset>  \n" );
		sb.append( "		<asset Note=\"资产明细\">  \n" );
		sb.append( "			<assetid Note=\"资产明细标识\">7</assetid>  \n" );
		sb.append( "			<assetbatch Note=\"预转固批号\"></assetbatch>  \n" );
		sb.append( "			<assetcode Note=\"预转固序号\"></assetcode>  \n" );
		sb.append( "			<ifextend Note=\"是否扩容\">0</ifextend>  \n" );
		sb.append( "			<parentcardbatch Note=\"父卡片批号\"></parentcardbatch>  \n" );
		sb.append( "			<parentcardcode Note=\"父卡片序号\"></parentcardcode>  \n" );
		sb.append( "			<category Note=\"资产目录\">03020405</category>  \n" );
		sb.append( "			<assetname Note=\"资产名称\">测试接线排</assetname>  \n" );
		sb.append( "			<unitsname Note=\"计量单位\">块</unitsname>  \n" );
		sb.append( "			<countnum Note=\"数量\">8</countnum>  \n" );
		sb.append( "			<productgrouptype Note=\"型号规格程式\">普天JPX01型</productgrouptype>  \n" );
		sb.append( "			<place Note=\"所在地点\">高新</place>  \n" );
		sb.append( "			<placecode Note=\"所在地点编码\">AJB.GXJ00</placecode>  \n" );
		sb.append( "			<buydate Note=\"购建日期\">20060314</buydate>  \n" );
		sb.append( "			<usedate Note=\"使用时间\"></usedate>  \n" );
		sb.append( "			<providertexno Note=\"生产厂商税号\"></providertexno>  \n" );
		sb.append( "			<providername Note=\"生产厂商名称\">南京普天通信股份有限公司</providername>  \n" );
		sb.append( "			<deviceprovidertexno Note=\"设备供应商税号\"></deviceprovidertexno>  \n" );
		sb.append( "			<deviceprovidername Note=\"设备供应商名称\">南京普天通信股份有限公司</deviceprovidername>  \n" );
		sb.append( "			<ascription Note=\"资产归属\"></ascription>  \n" );
		sb.append( "			<speciality Note=\"专业属性\"></speciality>  \n" );
		sb.append( "			<designno Note=\"所附设计图纸图号\"></designno>  \n" );
		sb.append( "			<contractprice Note=\"设备购买价\">2688</contractprice>  \n" );
		sb.append( "			<relatefee Note=\"设备采购支付相关费用\">0</relatefee>  \n" );
		sb.append( "			<!--以下是新增的-->  \n" );
		sb.append( "			<principal Note=\"责任人\"></principal>  \n" );
		sb.append( "			<userdepartment Note=\"使用部门\"></userdepartment>  \n" );
		sb.append( "			<increasereason Note=\"资产增加原因\">在建工程导入</increasereason><!--根据项目的是否是单纯购置来判断   在建工程转入或单纯购置-->  \n" );
		sb.append( "			<practicalityno Note=\"实物资产编号\"></practicalityno><!--待确定编码方式-->  \n" );
		sb.append( "			<memo Note=\"备注\">asdfa</memo>			  \n" );
		sb.append( "		</asset>  \n" );
		sb.append( " </toaccounting> \n" );
		sb.append( "</IfInfo> \n" );

		String path = System.getProperty( "user.dir" ) + System.getProperty( "file.separator" ) + "fromfinancelog";
		FileUtil fu = new FileUtil();
		try {
			fu.createFolder( path );
			fu.createFile( path + System.getProperty( "file.separator" ) + "sdsd.xml", sb.toString() );
			log.info( path + System.getProperty( "file.separator" ) + "sdsd.xml" );
		} catch( Exception e ) {
		}
		// String xmlfile =
		// com.xwtec.ApplicationObject.realPath+System.getProperty("file.separator")+"attachments"+
		// System.getProperty("file.separator")+"contractid.xml";
		// log.info();
		// log.info("class header:"+vssHeader);
	}

}