package com.autotest.run;

import org.junit.Rule;
import org.junit.Test;

import com.autotest.core.DriverService;
import com.autotest.core.sendHttp;
import com.autotest.entity.request.ReqEntity_redpackets;
import com.autotest.entity.response.ResponseArray;

public class run {
	@Rule
	public DriverService ds=new DriverService();
	@Test
	public void test1(){
		new sendHttp().send(new ReqEntity_redpackets());
		System.out.println(ResponseArray.getInstance("test1[1]", "redpackets").getData());
	}
}
