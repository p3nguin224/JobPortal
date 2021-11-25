package com.jobportal.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.UserDAO;
import com.jobportal.user.domain.User;

@Service
public class UserSecurityService implements UserDetailsService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDAO.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username Not Found");
		}
		
		
		return user;
	}

}
