package com.mashibing;

import com.mashibing.bean.Emp;
import com.mashibing.dao.EmoDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: Nic1
 * @Date: 2022/10/31 22:53
 * @Theme:
 */
public class MyBatisTest02 {
    public static void main(String[] args) {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);//★★，使用输入流（配置文件输入流），构建 DefaultSqlSessionFactory对象
        // 获取数据库会话，注意此时还未与数据库建立连接
        SqlSession sqlSession = sqlSessionFactory.openSession();//★★
        List<Emp> list = null;
        Emp em = null;
        try {
            // 从sqlSession对象的configuration属性的mapperRegistry属性，获取要调用的接口类，注意，返回的是一个代理对象！
            EmoDao mapper2 = sqlSession.getMapper(EmoDao.class);//★★
            // 调用代理对象Mapper的方法，实际调用的是InvocationHandler的invoke方法
            em = mapper2.findEmpByEmpno(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        list.forEach(emp -> {
            System.out.println(emp.toString());
        });
        //System.out.println(em);
    }
}
