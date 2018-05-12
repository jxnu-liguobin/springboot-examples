package cn.edu.jxnu.mongoTest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.edu.jxnu.mongoTest.entity.User;

/**
 * 类似JPA
 * 
 * 定义基础CRUD操作DAO
 */
public interface UserRepository extends MongoRepository<User, String> {

	User findByName(String name);
}
