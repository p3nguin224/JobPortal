package com.jobportal.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.jobportal.user.dao.CompanyProfileDAO;
import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.CompanyProfileService;

public class CompanyProfileServiceImpl implements CompanyProfileService{
	
	@Autowired
	private CompanyProfileDAO companyProfileDAO;

	@Override
	public CompanyProfile findByUser(User user) {
		// TODO Auto-generated method stub
		return companyProfileDAO.findByUser(user);
	}

	@Override
	public CompanyProfile save(CompanyProfile companyProfile) {
		// TODO Auto-generated method stub
		return companyProfileDAO.save(companyProfile);
	}

	@Override
	public CompanyProfile findById(Long id) {
		// TODO Auto-generated method stub
		return companyProfileDAO.findById(id).get();
	}

}
