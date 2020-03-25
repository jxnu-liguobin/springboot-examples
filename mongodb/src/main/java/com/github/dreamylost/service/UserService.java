package com.github.dreamylost.service;

import org.springframework.stereotype.Repository;

import com.github.dreamylost.entity.User;

/**
 * 定义业务特有的CRUD接口
 */
@Repository
public interface UserService {
	void save(User user);

	User findByName(String name);

}
