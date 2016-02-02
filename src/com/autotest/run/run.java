package com.autotest.run;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;
import org.apache.ibatis.session.SqlSession;
import org.junit.Rule;
import org.junit.Test;
import com.autotest.build.DB;
import com.autotest.core.DriverService;
import com.autotest.core.sendHttp;
import com.autotest.entity.response.ResponseArray;
import com.autotest.interfaces.RequestsMapper;
import com.autotest.interfaces.RunnersMapper;
import com.autotest.model.Fields;
import com.autotest.model.Requests;
import com.autotest.model.Runners;
import com.autotest.model.Sets;

public class run {
	@Rule
	public DriverService ds=new DriverService();
	//@Test
	public void test1(){
//		new sendHttp().send(new ReqEntity_redpackets());
		System.out.println(ResponseArray.getInstance("test1[1]", "redpackets").getMsg());
	}
//	@Test
	public void testName(){
		SqlSession session = DB.getSession();
		RunnersMapper runner = session.getMapper(RunnersMapper.class);
		List<Requests> requests=runner.getRequests(1);
		RequestsMapper reqmap=session.getMapper(RequestsMapper.class);
		for(Requests request:requests){
			System.out.println(request.getName());
			for(Fields field:reqmap.getFields(request.getId())){
				System.out.println(field.getName());
			}
			
		}
	}
	@Test
	public void test11(){
		Properties pro=System.getProperties();
		System.out.println(pro.getProperty("os.name"));
	}
}
