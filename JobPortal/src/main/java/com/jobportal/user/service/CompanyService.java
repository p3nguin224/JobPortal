package com.jobportal.user.service;

import com.jobportal.user.domain.Company;

public interface CompanyService {
	
	Company findByCompanyName(String cpmpanyName);
	
	Company findByCompanyId(String companyId);

}
