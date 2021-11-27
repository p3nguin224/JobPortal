package com.jobportal.user.service;

import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.PasswordResetToken;
import com.jobportal.user.domain.security.UserRole;

public interface UserService {
	
	User findByUsername (String username);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	User save(User user);
	
	User createUser(User user, UserRole userRole);
	
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	

}
