package com.autotest.entity.response;

import java.util.HashMap;

import com.autotest.core.DriverService;

public class ResponseArray {
	private static ResponseArray single = null;
	private HashMap<String, HashMap<String, Response>> arraylist = new HashMap<>();
	public void setArraylist(String setName,String interName,Response response){
		HashMap<String, Response> value = new HashMap<String, Response>();
		value.put(interName, response);
		ResponseArray.getInstance().arraylist.put(setName,value);
		return ;
	}
	public Response getResponse(String setName,String interName){
		if(arraylist.containsKey(setName)){
			if(arraylist.get(setName).containsKey(interName)){
				return arraylist.get(setName).get(interName);
			}
		}
		//检测无该Response返回null
		return null;
	}
	private ResponseArray(){
		
	}
	//静态工厂方法
	public static ResponseArray getInstance(){
		if(single == null){
			single = new ResponseArray();
		}
		return single;
	}
	public static Response getInstance(String setName,String interName){
		if(single == null){
			single = new ResponseArray();
		}
		return single.getResponse(setName, interName);
	}
	/**
	 * 获取带#号分割的字符串值
	 * */
	public static String getJinghao(String val){
		if(val.contains("#")){
			String []arr=val.split("#");
			String []arr1=arr[0].split("[.]");
			if(arr1[0].contains("[NUM]")){
				arr1[0]=arr1[0].split("\\[")[0]+"["+DriverService.number+"]";
			}
			return ResponseArray.getInstance(arr1[0], arr1[1]).getValue("resp."+arr[1]);
		}
		return val;
	}
}
