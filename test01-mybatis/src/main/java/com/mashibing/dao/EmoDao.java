package com.mashibing.dao;

import com.mashibing.bean.Emp;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: Nic1
 * @Date: 2022/10/31 22:06
 * @Theme:
 */
public interface EmoDao {

    //@Select("select * from emp where empno = #{empno}")
    public Emp findEmpByEmpno(Integer empno);
}
