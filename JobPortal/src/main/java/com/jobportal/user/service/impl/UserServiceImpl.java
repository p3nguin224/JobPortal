package com.jobportal.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.PasswordResetTokenDAO;
import com.jobportal.user.dao.RoleDAO;
import com.jobportal.user.dao.UserDAO;
import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.PasswordResetToken;
import com.jobportal.user.domain.security.UserRole;
import com.jobportal.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDAO; 

	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private PasswordResetTokenDAO passwordResetTokenDAO;

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userDAO.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userDAO.findByEmail(email);
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userDAO.findById(id).get();
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userDAO.save(user);
	}

	@Override
	public User createUser(User user, UserRole userRole) {
		// TODO Auto-generated method stub
		User localUser = userDAO.findByEmail(user.getEmail());
		
		if (localUser!= null) {
			LOG.info("user {} already exists. Nothing will be done", user.getEmail());
		} else {
			roleDAO.save(userRole.getRole());
			user.setUserRole(userRole);
			localUser = userDAO.save(user);
		}
		return localUser;
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		// TODO Auto-generated method stub
		return passwordResetTokenDAO.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		// TODO Auto-generated method stub
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenDAO.save(myToken);
	}

}
