package com.jobportal.user;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSkill;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.Role;
import com.jobportal.user.domain.security.UserRole;
import com.jobportal.user.service.JobService;
import com.jobportal.user.service.SkillService;
import com.jobportal.user.service.UserService;
import com.jobportal.user.utility.SecurityUtility;

@SpringBootApplication
public class JobPortalApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private SkillService skillService;

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
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		
		userRoles1.setUser(user1);
		userRoles1.setRole(role1);
		
		userService.createUser(user1, userRoles1);
		
		////////////// Make test job
//		Job job1 = new Job();
//		job1.setJobTitle("TestingJob");
//		job1.setCategory("IT");
//		job1.setMinBudget(300);
//		job1.setMaxBudget(500);
//		job1.setDeadlineDate("2010-12-02");
//		job1.setDescription("testing123");
//		job1.setRequiredMember(5);
//		job1.setLocation("myanmar");
//		job1.setStatus("avaliable");
//		job1.setLastAppliedDate("2010-11-03");
//		job1.setRequiredLanguage("eng");
//		job1.setPostedDate(LocalDate.now().toString());
//		
//		Skill skill1 = new Skill();
//		skill1.setSkillName("html");
//		skillService.saveSkill(skill1);
//		JobSkill jobskill1 = new JobSkill(job1, skill1);
//		
//		Skill skill2 = new Skill();
//		skill2.setSkillName("java");
//		skillService.saveSkill(skill2);
//		JobSkill jobskill2 = new JobSkill(job1, skill2);
//		
//		List<JobSkill> jobskillList = Arrays.asList(jobskill1,jobskill2);
//		
//		jobService.createJob(job1, jobskillList);
//		
		
	}

}
