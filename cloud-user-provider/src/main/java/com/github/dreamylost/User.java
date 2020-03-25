package com.github.dreamylost;

/**
 * @author 梦境迷离
 * @version V1.0
 * @time 018年4月18日
 */
public class User {

	private Integer id;

	private String name;

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
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

	public User(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public User() {
		super();
	}
}
