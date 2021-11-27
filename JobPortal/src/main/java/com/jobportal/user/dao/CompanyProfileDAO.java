package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.User;

public interface CompanyProfileDAO extends CrudRepository<CompanyProfile, Long>{
	CompanyProfile findByUser(User user);
	
}
