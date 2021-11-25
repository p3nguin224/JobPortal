package com.jobportal.user.service;

import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;

public interface JobSeekerProfileService {
	JobSeekerProfile findByUser(User user);
	
	JobSeekerProfile save(JobSeekerProfile jobSeekerProfile);
	
	JobSeekerProfile findById(Long id);
	

}
