package com.jobportal.user.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;

public interface JobSeekerProfileDAO extends CrudRepository<JobSeekerProfile, Long>{
	JobSeekerProfile findByUser(User user);
	
	List<JobSeekerProfile> findByCategory(String category);
}
