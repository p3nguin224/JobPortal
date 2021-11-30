package com.jobportal.user.dao;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.PasswordResetToken;

public interface PasswordResetTokenDAO extends CrudRepository<PasswordResetToken, Long>{
	
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
	
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	

}
