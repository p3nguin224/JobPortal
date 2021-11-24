package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.User;

public interface UserDAO extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
	
	User findByEmail(String email);

}
