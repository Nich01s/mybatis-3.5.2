package com.mashibing;

import com.mashibing.bean.Emp;
import com.mashibing.dao.EmoDao;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Nic1
 * @Date: 2022/10/31 17:00
 * @Theme: 模拟反向代理创建接口代理对象，实现SQL语句调用执行过程
 */

public class MyBatisTest01 {
    public static void main(String[] args) {

        // 对Dao接口进行代理，接口中含sql语句
        EmoDao emoDao = (EmoDao) Proxy.newProxyInstance(MyBatisTest01.class.getClassLoader(), new Class[]{EmoDao.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 模拟获取注解中的sql语句
                Select ann = method.getAnnotation(Select.class);
                String[] value = ann.value();
                // 模拟解析方法参数
                Map<String, Object> map = parseArgs(method, args);
                String sql = value[0];
                // 模拟解析sql语句
                System.out.println(parseSql(sql, map));

                // 数据库操作

                // 处理结果集

                return null;
            }
        });
        // 调用接口
        Emp empno = emoDao.findEmpByEmpno(123);
    }

    // 解析方法参数
    public static Map<String, Object> parseArgs(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>();
        int[] index = {0};
        // 获取方法参数
        Parameter[] params = method.getParameters();
        Arrays.asList(params).forEach(param -> {
            String name = param.getName();// 获取参数名
            map.put(name, args[index[0]++]);
        });
        return map;
    }

    // 解析sql语句
    public static String parseSql(String sql, Map<String, Object> map) {
        for (String s : map.keySet()) {
            String oldStr = "#{" + s + "}";
            sql = sql.replace(oldStr, "'"+map.get(s).toString()+"'");
        }
        return sql;
    }

}
