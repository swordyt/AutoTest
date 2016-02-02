package com.autotest.build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.autotest.interfaces.RequestsMapper;
import com.autotest.interfaces.RunnersMapper;
import com.autotest.model.Fields;
import com.autotest.model.Requests;
import com.autotest.model.Sets;

public class Make {

	//args[0]=DB数据库构建，=CONFIG配置文件构建，默认配置文件构建
	public static void main(String[] args) {
		Properties pro = System.getProperties();
		System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOW");
		if(args.length>0){
			if(args[0].equalsIgnoreCase("DB")){
				makeByDB();
				return ;
			}
		}
		makeByPro();
	}
	public static void makeByPro(){
		String path=System.getProperty("user.dir")+"/src/com/autotest/entity/request/ReqEntity_";
		if(System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOW")){
			path=System.getProperty("user.dir")+"\\src\\com\\autotest\\entity\\request\\ReqEntity_";
		}
		Properties pro = PropertiesTool.readProperties("/com/autotest/config/config.properties");
		String []confs = pro.getProperty("interface").split(",");
		for(String conf:confs){
			File file = new File(path+conf+".java");
			if(!file.exists()){
				System.out.println("创建文件：ReqEntity_"+conf+".java");
				Properties proConf = PropertiesTool.readProperties("/com/autotest/config/config.properties");
				String []fields = proConf.getProperty(conf+".fields").split(",");
				makeRequest(conf,Arrays.asList(fields));
				System.out.println("创建文件："+conf+".xlsx");
				makeExcel(conf, Arrays.asList(fields));
			}
		}
	}
	public static void makeByDB(){
		SqlSession session = DB.getSession();
		RunnersMapper map=session.getMapper(RunnersMapper.class);
		RequestsMapper requestMap = session.getMapper(RequestsMapper.class);
		for(Sets set:map.getSets("wait")){
			List<Requests> requests=map.getRequests(set.getId());
			for(Requests request:requests){
				List<String> fields = new ArrayList<String>();
				int i=0;
				for(Fields field:requestMap.getFields(request.getId())){
					fields.add(field.getName());
				}
				Make.makeRequest(request.getName(),fields);
				Make.makeExcel(request.getName(),fields);
			}
			
		}
	}
	public static void makeRequest(String clsName,List<String> fields){
		String content = "";
		String requestPath = System.getProperty("user.dir")+"/src/com/autotest/entity/request/ReqEntity_"+clsName+".java";
		if(System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOW")){
			requestPath=System.getProperty("user.dir")+"\\src\\com\\autotest\\entity\\request\\ReqEntity_"+clsName+".java";
		}
		File request = new File(requestPath);
		content = "package com.autotest.entity.request;\n"
				+ "public class ReqEntity_"+clsName+" extends RequestEntity {\n"
						+ "public ReqEntity_"+clsName+"(){\n"
							+"initData();\n"
			+"}\n";
		String param = "";
		String method = "";
		for(String para:fields){
			String value = "";
			param = param + "private String "+para+";\n";
			value = para.substring(0, 1).toUpperCase()+para.substring(1);
			method = method + "public String get"+value+"(){\n"
					+ "return this."+para+";\n"
							+ "}\n"
							+ "public void set"+value+"(String value){\n"
									+ "this."+para+"="+"value;\n"
									+ "}\n";
			
		}
		content = content + param + method + "}";
		try {
			request.createNewFile();
			FileOutputStream out = new FileOutputStream(request);
			out.write(content.getBytes("GBK"));
			out.close();
		} catch (Exception e) {
			System.out.println("生成request实体类失败，"+e.getMessage());
			e.printStackTrace();
		}
	}
	public static void makeExcel(String clsName,List<String> fields){
		String path=System.getProperty("user.dir")+"/src/com/autotest/data/";
		if(System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOW")){
			path=System.getProperty("user.dir")+"\\src\\com\\autotest\\data\\";
		}
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("data");
		//创建一行
		Row row = sheet.createRow(0);
		for(int i=0;i<fields.size();i++){
			Cell cell = row.createCell(i);
			cell.setCellValue(fields.get(i));
		}
		//创建文件流
		try {
			OutputStream out = new FileOutputStream(path+clsName+".xlsx");
			wb.write(out);
			out.close();
		} catch (Exception e) {
			System.out.println("创建xlsx文件失败，"+e.getMessage());
			e.printStackTrace();
		}
	}
}
