package com.github.dreamylost.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 封装多个学生实体类
 *
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:42:14
 * @version： V1.0
 */
@XmlRootElement(name = "Students")
public class Students {

    private List<Student> students;

    public Students(List<Student> students) {
        super();
        this.students = students;
    }

    @XmlElement(name = "Student")
    public List<Student> getStudents() {
        return students;
    }

    public Students() {
        super();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
