package com.jobportal.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.Role;
import com.jobportal.user.domain.security.UserRole;
import com.jobportal.user.service.UserService;
import com.jobportal.user.utility.SecurityUtility;

@SpringBootApplication
public class JobPortalApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		User user1 = new User();
		
		user1.setDob("testDOB");
		user1.setEmail("captainmushroom.224@gmail.com");
		user1.setGender("male");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("1234"));
		user1.setRegisteredDate("2020");
		user1.setUsername("cap");
		
		UserRole userRoles1 = new UserRole();
		
		Role role1 = new Role();
		role1.setRoleId(2);
		role1.setName("ROLE_COMPANY");
		
		userRoles1.setUser(user1);
		userRoles1.setRole(role1);
		
		userService.createUser(user1, userRoles1);
		
	}

}
