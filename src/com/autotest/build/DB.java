package com.autotest.build;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.autotest.core.DriverService;
import com.autotest.interfaces.RequestsMapper;
import com.autotest.model.Fields;

public class DB {
	public static SqlSessionFactory sqlSessionFactory;
	public static Reader reader;
	private static DB single;
	static{
		//初始化数据库
		try {
			reader=Resources.getResourceAsReader("com/autotest/config/MyBatis-config.xml");
			sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			DriverService.logger.error("初始化数据库连接失败：");
			e.printStackTrace();
		}
	}
	private static DB getInstance(){
		if(single == null){
			single = new DB();
		}
			return single;
	}
	public static SqlSessionFactory getSessionFactory(){
		return getInstance().sqlSessionFactory;
	}
	public static SqlSession getSession(){
	SqlSession session =DB.getSessionFactory().openSession();
		return session;
	}
	public static void main(String[] args) {
/*		增加数据
 * 		SqlSession session=DB.getSession();
		Requests req=new Requests();
		req.setDataaddress("test");
		req.setName("test");
		RequestsMapper map=session.getMapper(RequestsMapper.class);
		map.addRequests(req);
		session.commit(true);
		System.out.println(req.getId());*/
		SqlSession session = DB.getSession();
//		RequestsMapper map=session.getMapper(RequestsMapper.class);
		
/*		更新
 * 		Requests req1=map.selectRequestByID(41);
		req1.setName("test1");
		req1.setDataaddress("test1");
		map.updateRequests(req1);
		session.commit(true);*/
/*		删除
		 * map.deleteRequest(41);*/
/*		List<Fields> fields=map.getFields(31);
		for(Fields field:fields){
			System.out.println(field.getName());
		}*/
//		session.commit(true);
	}
}
