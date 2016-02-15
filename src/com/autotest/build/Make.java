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

import com.autotest.core.sendHttp;
import com.autotest.entity.request.ReqEntity_login;
import com.autotest.enums.DriverType;
import com.autotest.interfaces.RequestsMapper;
import com.autotest.interfaces.RunnersMapper;
import com.autotest.model.Fields;
import com.autotest.model.Requests;
import com.autotest.model.Sets;

public class Make {

	// args[0]=DB数据库构建，=CONFIG配置文件构建，默认配置文件构建
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("DB")) {
				makeByDB();
			}
		}else{
			makeByPro();
		}
		makeRun();
	}

	public static void makeByPro() {
		String path = "/data1/www/autotest/src/com/autotest/entity/request/ReqEntity_";
		if (System.getProperties().getProperty("os.name").toUpperCase()
				.contains("WINDOW")) {
			path = System.getProperty("user.dir")
					+ "\\src\\com\\autotest\\entity\\request\\ReqEntity_";
		}
		Properties pro = PropertiesTool
				.readProperties("/com/autotest/config/config.properties");
		String[] confs = pro.getProperty("interface").split(",");
		for (String conf : confs) {
			File file = new File(path + conf + ".java");
			if (!file.exists()) {
				System.out.println("创建文件：ReqEntity_" + conf + ".java");
				Properties proConf = PropertiesTool
						.readProperties("/com/autotest/config/config.properties");
				String[] fields = proConf.getProperty(conf + ".fields").split(
						",");
				makeRequest(conf, Arrays.asList(fields));
				System.out.println("创建文件：" + conf + ".xlsx");
				makeExcel(conf, Arrays.asList(fields));
			}
		}
	}

	public static void makeByDB() {
		SqlSession session = DB.getSession();
		RunnersMapper runMap = session.getMapper(RunnersMapper.class);
		RequestsMapper requestMap = session.getMapper(RequestsMapper.class);
		for (Sets set : runMap.getSets("wait")) {
			List<Requests> requests = runMap.getRequests(set.getId());
			for (Requests request : requests) {
				List<String> fields = new ArrayList<String>();
				int i = 0;
				for (Fields field : requestMap.getFields(request.getId())) {
					fields.add(field.getName());
				}
				Make.makeRequest(request.getName(), fields);
				Make.makeExcel(request.getName(), fields);
			}

		}
	}

	public static void makeRun() {
		String runPath = "/data1/www/autotest/src/com/autotest/run/run.java";
		if (System.getProperties().getProperty("os.name").toUpperCase()
				.contains("WINDOW")) {
			runPath = System.getProperty("user.dir")
					+ "\\src\\com\\autotest\\run\\run.java";
		}
		File run = new File(runPath);
		String contentLeft = "package com.autotest.run;\n";
		String importContent = "import org.junit.Rule;\n"
				+ "import org.junit.Test;\n"
				+ "import com.autotest.annotation.DataSource;\n"
				+ "import com.autotest.core.DriverService;\n"
				+ "import com.autotest.core.sendHttp;\n"
				+ "import com.autotest.enums.DataType;\n"
				+ "import com.autotest.enums.DriverType;\n"
				+ "import com.autotest.entity.request.*;\n";
		String contentClass = "public class run {\n" + "@Rule\n"
				+ "public DriverService ds=new DriverService();\n";
		String contentRight = "}";
		String content = "";
		SqlSession session = DB.getSession();
		RunnersMapper runMap = session.getMapper(RunnersMapper.class);
		RequestsMapper requestsMap = session.getMapper(RequestsMapper.class);
		for (Sets set : runMap.getSets("wait")) {
			String driverType = "Drive=DriverType.excel";
			String dataType = "minmax=DataType.min";
			if (set.getDrivertype() == 1) {
				driverType = "Drive=DriverType.constant";
				dataType = "count=" + set.getDatatype();
			} else {
				if (set.getDatatype() == 1) {
					dataType = "minmax=DataType.max";
				}
			}
			content = content + "@Test\n" + "@DataSource(" + driverType + ","
					+ dataType + ")\n" + "public void " + set.getName()
					+ "(){\n";

			for (Requests request : runMap.getRequests(set.getId())) {
				content = content + "new sendHttp().send(new ReqEntity_"
						+ request.getName() + "());\n";
			}
			content = content + "}\n";
		}
		String txt = contentLeft + importContent + contentClass + content
				+ contentRight;
		try {
			run.createNewFile();
			FileOutputStream out = new FileOutputStream(run);
			out.write(txt.getBytes("GBK"));
			out.close();
		} catch (Exception e) {
			System.out.println("生成run类失败，" + e.getMessage());
			e.printStackTrace();
		}

	}

	public static void makeRequest(String clsName, List<String> fields) {
		String content = "";
		String requestPath = "/data1/www/autotest/src/com/autotest/entity/request/ReqEntity_"
				+ clsName + ".java";
		if (System.getProperties().getProperty("os.name").toUpperCase()
				.contains("WINDOW")) {
			requestPath = System.getProperty("user.dir")
					+ "\\src\\com\\autotest\\entity\\request\\ReqEntity_"
					+ clsName + ".java";
		}
		File request = new File(requestPath);
		content = "package com.autotest.entity.request;\n"
				+ "public class ReqEntity_" + clsName
				+ " extends RequestEntity {\n" + "public ReqEntity_" + clsName
				+ "(){\n" + "initData();\n" + "}\n";
		String param = "";
		String method = "";
		for (String para : fields) {
			String value = "";
			param = param + "private String " + para + ";\n";
			value = para.substring(0, 1).toUpperCase() + para.substring(1);
			method = method + "public String get" + value + "(){\n"
					+ "return this." + para + ";\n" + "}\n" + "public void set"
					+ value + "(String value){\n" + "this." + para + "="
					+ "value;\n" + "}\n";

		}
		content = content + param + method + "}";
		try {
			request.createNewFile();
			FileOutputStream out = new FileOutputStream(request);
			out.write(content.getBytes("GBK"));
			out.close();
		} catch (Exception e) {
			System.out.println("生成request实体类失败，" + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void makeExcel(String clsName, List<String> fields) {
		String path = "/data1/www/autotest/src/com/autotest/data/";
		if (System.getProperties().getProperty("os.name").toUpperCase()
				.contains("WINDOW")) {
			path = System.getProperty("user.dir")
					+ "\\src\\com\\autotest\\data\\";
		}
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("data");
		// 创建一行
		Row row = sheet.createRow(0);
		for (int i = 0; i < fields.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(fields.get(i));
		}
		// 创建文件流
		try {
			OutputStream out = new FileOutputStream(path + clsName + ".xlsx");
			wb.write(out);
			out.close();
		} catch (Exception e) {
			System.out.println("创建xlsx文件失败，" + e.getMessage());
			e.printStackTrace();
		}
	}
}
