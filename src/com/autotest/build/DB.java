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
}
