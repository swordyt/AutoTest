package com.autotest.entity.request;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.autotest.build.ExcelUtil;
import com.autotest.core.DriverService;
import com.autotest.entity.response.ResponseArray;
import com.autotest.enums.DataType;
import com.autotest.interfaces.Request;

public class RequestEntity implements Request {
	private String domain="http://api.yangbo.qa.anhouse.com.cn";
	private String url="/user/user/signAndCredit.html";
	private String method="post";
	private String name="test";
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}

	
	
	
	@Override
	//��֤url�ĸ�ʽΪ/work
		public String makeUrl() {
			if(!this.url.startsWith("/")){
				this.url = "/"+this.url;
			}
			if(this.url.endsWith("/")){
				this.url = this.url.substring(0, this.url.length()-1);
			}
			if(this.domain.toLowerCase().startsWith("http://")){
				this.domain = this.domain.substring(7, this.domain.length());
			}
			if(this.domain.endsWith("/")){
				this.domain = this.domain.substring(0,this.domain.length()-1);
			}
			return "http://"+this.domain+this.url;
		}


	@Override
	public String makeParam() throws Exception{
		//��ȡʵ������������ԣ�����Field����
		Field []field = this.getClass().getDeclaredFields();
		String param = ""; 
		for(int j=0;j<field.length;j++){//������������
				String name = field[j].getName();//��ȡ��������
				String upName = name.substring(0, 1).toUpperCase()+name.substring(1);
				Method m = this.getClass().getMethod("get"+upName);
				String value = (String)m.invoke(this);
				if(name == "url" || name == "domain" || name == "method" || name == "num"){
					continue;
				}
				if(value == ""){
					continue;
				}
				if(param == ""){
					param = name+"="+value;
					continue;
				} 
				param = param+"&"+name+"="+value;
		}
		
		return param;
	}
	
	protected void initData(){
		String path="/data1/www/autotest/src/com/autotest/data/";
		if(System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOW")){
			path=System.getProperty("user.dir")+"\\src\\com\\autotest\\data\\";
		}
		List<Map<String,String>> data = new ExcelUtil().excelDatas(path
				+this.getClass().toString().split("ReqEntity_")[1]+".xlsx", "data");
		Map<String, String> map = null;
		if(DriverService.datatype == DataType.min){
			if(DriverService.times == 1 || DriverService.times > data.size()){
				DriverService.times = data.size();
			}
		}
		if(DriverService.datatype == DataType.max){
			if(DriverService.times == 1 || DriverService.times < data.size()){
				DriverService.times = data.size();
			}
		}
		if(data.size() <DriverService.number){
			map = data.get((DriverService.number-1)%data.size());
		}else{
			map = data.get(DriverService.number-1);
		}
		for(Map.Entry<String, String> entry:map.entrySet()){
			String key = entry.getKey().toString();
			String upKey = key.substring(0, 1).toUpperCase()+key.substring(1);
			String value = entry.getValue().toString();
			value = ResponseArray.getJinghao(value);
			try {
				Method m = this.getClass().getMethod("set"+upKey, String.class);
				m.invoke(this, value);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

}
