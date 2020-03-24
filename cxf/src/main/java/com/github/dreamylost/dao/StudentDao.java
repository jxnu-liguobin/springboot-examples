package com.github.dreamylost.dao;

import com.github.dreamylost.entity.Student;
import org.apache.ibatis.annotations.Param;

/**
 * dao层数据操作接口
 *
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:41:43
 * @version： V1.0
 */
public interface StudentDao {

    Student getStudentById(@Param("id") Integer id);
}
