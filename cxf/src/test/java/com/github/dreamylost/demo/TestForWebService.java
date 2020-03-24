package com.github.dreamylost.demo;

import com.github.dreamylost.DemoApplication;
import com.github.dreamylost.entity.Student;
import com.github.dreamylost.service.StudentService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 单元测试 测试整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class TestForWebService extends TestCase {
    @Autowired
    private StudentService studentRestfulService;

    /**
     * 测试service与dao层
     */
    @Test
    public void Test() {
        Student s = studentRestfulService.getStudent(1);
        System.out.println(s.toString());
    }

}
