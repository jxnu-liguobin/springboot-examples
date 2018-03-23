package cn.edu.jxnu.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 学生实体类
 * 
 * @author： liguobin
 * @Description：
 * @时间： 2018-3-7 下午3:42:04
 * @version： V1.0
 * 
 */
@XmlRootElement(name = "Student")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private char sex;
	private String address;
	private Integer age;

	public Student() {
		super();
	}

	public Student(Integer id, String name, char sex, String address,
			Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", address=" + address + ", age=" + age + "]";
	}

}
