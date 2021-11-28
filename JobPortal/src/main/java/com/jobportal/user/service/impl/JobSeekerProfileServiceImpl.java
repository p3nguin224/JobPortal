package com.jobportal.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.JobSeekerProfileDAO;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.JobSeekerProfileService;

@Service
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService{
	
	@Autowired
	private JobSeekerProfileDAO jobSeekerDAO;

	@Override
	public JobSeekerProfile findByUser(User user) {
		// TODO Auto-generated method stub
		return jobSeekerDAO.findByUser(user);
	}

	@Override
	public JobSeekerProfile save(JobSeekerProfile jobSeekerProfile) {
		// TODO Auto-generated method stub
		return jobSeekerDAO.save(jobSeekerProfile);
	}

	@Override
	public JobSeekerProfile findById(Long id) {
		// TODO Auto-generated method stub
		return jobSeekerDAO.findById(id).get();
	}

	@Override
	public List<JobSeekerProfile> findAll() {
		// TODO Auto-generated method stub
		List<JobSeekerProfile> jobSeekerList = ((List<JobSeekerProfile>) jobSeekerDAO.findAll()).stream().filter(jobSeeker -> jobSeeker.getStatus().equals("avaliable")).collect(Collectors.toList());
		
		return jobSeekerList;
	}

	@Override
	public List<JobSeekerProfile> findByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}



}
