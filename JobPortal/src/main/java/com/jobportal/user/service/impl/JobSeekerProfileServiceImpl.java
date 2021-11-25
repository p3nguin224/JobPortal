package com.jobportal.user.service.impl;

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


}
