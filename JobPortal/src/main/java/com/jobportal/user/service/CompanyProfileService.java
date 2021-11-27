package com.jobportal.user.service;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.User;

public interface CompanyProfileService {
	CompanyProfile findByUser(User user);
	
	CompanyProfile save(CompanyProfile companyProfile);
	
	CompanyProfile findById(Long id);
}
