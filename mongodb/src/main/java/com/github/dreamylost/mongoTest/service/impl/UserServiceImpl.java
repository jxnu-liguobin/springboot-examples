package com.github.dreamylost.mongoTest.service.impl;

import com.github.dreamylost.mongoTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dreamylost.mongoTest.entity.User;
import com.github.dreamylost.mongoTest.service.UserService;

/**
 * 实现业务的操作，并封装MongoTemplate
 * 
 * 此处并未封装
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	public void save(User user) {
		userRepository.save(user);
	}

	public User findByName(String name) {
		return this.userRepository.findByName(name);
	}

}
