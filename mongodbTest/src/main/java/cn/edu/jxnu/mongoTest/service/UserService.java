package cn.edu.jxnu.mongoTest.service;

import org.springframework.stereotype.Repository;

import cn.edu.jxnu.mongoTest.entity.User;

/**
 * 定义业务特有的CRUD接口
 */
@Repository
public interface UserService {
	void save(User user);

	User findByName(String name);

}
