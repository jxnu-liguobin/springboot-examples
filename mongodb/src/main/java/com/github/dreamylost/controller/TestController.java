package com.github.dreamylost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.dreamylost.entity.User;
import com.github.dreamylost.service.UserService;

@RestController
public class TestController {
	@Autowired
	private UserService userService;
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 新增
	 * 
	 * @return
	 */
	@GetMapping("/save")
	public User save() {
		User user = new User((int) (Math.random() * 100), "mongodb", 21);
		mongoTemplate.save(user);// 简便，没有封装在服务层
		return user;
	}

	/**
	 * 无参数查询
	 * 
	 * @author 梦境迷离.
	 * @time 2018年5月12日
	 * @version v1.0
	 * @return list列表
	 */
	@GetMapping("/find")
	public List<User> find() {
		List<User> userList = mongoTemplate.findAll(User.class);
		return userList;
	}

	/**
	 * 有参数查询
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/findByName")
	public User findByName(@RequestParam("name") String name) {
		User user = userService.findByName(name);
		return user;
	}

}
