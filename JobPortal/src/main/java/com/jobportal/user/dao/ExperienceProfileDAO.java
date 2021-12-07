package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.ExperienceProfile;

public interface ExperienceProfileDAO extends CrudRepository<ExperienceProfile, Long>{
	
}
