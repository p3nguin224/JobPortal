package com.jobportal.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.EducationProfileDAO;
import com.jobportal.user.domain.EducationProfile;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.service.EducationProfileService;

@Service
public class EducationProfileServiceImpl implements EducationProfileService{
	
	@Autowired
	private EducationProfileDAO educatioinProfileDAO;

	@Override
	public List<EducationProfile> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EducationProfile saveEductationProfile(EducationProfile educationProfile) {
		// TODO Auto-generated method stub
		return educatioinProfileDAO.save(educationProfile);
	}

	@Override
	public void removeEducationProfile(EducationProfile educationProfile) {
		// TODO Auto-generated method stub
		educatioinProfileDAO.delete(educationProfile);
	}

	@Override
	public EducationProfile findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		educatioinProfileDAO.deleteById(id);
	}

}
