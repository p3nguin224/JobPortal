package com.jobportal.user.dao;


import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.Company;



public interface CompanyDAO extends CrudRepository<Company, Long> {
	
	
	
	Company findByCompanyId(Long companyId);
	
	Company findByCompanyName(String companyName);

}
