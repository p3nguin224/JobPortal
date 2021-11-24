package com.jobportal.user.service;

import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.Role;

public interface UserService {
	
	User findByUsername (String username);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	User save(User user);
	
	User createUser(User user, Role role);

}
