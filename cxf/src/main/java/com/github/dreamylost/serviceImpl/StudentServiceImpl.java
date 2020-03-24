package com.github.dreamylost.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.dreamylost.dao.StudentDao;
import com.github.dreamylost.entity.Student;
import com.github.dreamylost.entity.Students;
import com.github.dreamylost.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.ArrayList;

/**
 * 实现webservice接口，对外暴露 soap
 *
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:43:06
 * @version： V1.0
 */
@Component//由Spring管理
@WebService(endpointInterface = "cn.edu.jxnu.service.StudentService") // webservice接口的全类名
public class StudentServiceImpl implements StudentService {

    /**
     * 注入spring bean
     */
    @Autowired
    private StudentDao studentDao;

    @Override
    public Student getStudent(Integer id) {
        return studentDao.getStudentById(id);
    }

    /**
     * 没有测试正确性，不是本文重点
     */
    @Override
    public Students getAllStudent(String ids) {
        Students students = new Students(new ArrayList<Student>());
        // 得到json对象
        JSONObject json = JSONObject.parseObject(ids);
        // 获取对象的id列表
        JSONArray sid = json.getJSONArray("id");

        for (int i = 0; i < sid.size(); i++) {
            Integer s = sid.getInteger(0);
            if (s != null) {
                students.getStudents().add(studentDao.getStudentById(s));
            } else {
                continue;
            }
        }
        return students;

    }

}
