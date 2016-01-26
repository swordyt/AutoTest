package com.autotest.build;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.autotest.core.DriverService;
import com.autotest.entity.response.ResponseArray;
import com.autotest.interfaces.RequestsMapper;
import com.autotest.model.Requests;

public class DB {
	public static SqlSessionFactory sqlSessionFactory;
	public static Reader reader;
	private static DB single;
	static{
		//��ʼ�����ݿ�
		try {
			reader=Resources.getResourceAsReader("com/autotest/config/MyBatis-config.xml");
			sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			DriverService.logger.error("��ʼ�����ݿ�����ʧ�ܣ�");
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
//		RequestsMapper map = DB.getSession().getMapper(RequestsMapper.class);
//		Requests request = map.selectRequestByID(28);
//		System.out.println(request.getName());
//		List<Requests> list=map.selectRequests();
//		for(Requests req:list){
//			System.out.println(req.getDataaddress());
//		}
		SqlSession session=DB.getSession();
		RequestsMapper map=DB.getSession().getMapper(RequestsMapper.class);
//		Requests req=map.selectRequestByID(31);
//		map.addRequests(req);
		Requests req=new Requests();
		req.setName("test");
		req.setDataaddress("test");
		map.addRequests(req);
		session.commit();
		System.out.println(req.getId());
		session.close();
		
	}
}
