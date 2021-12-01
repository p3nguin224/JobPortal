package com.jobportal.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.ExperienceProfileDAO;
import com.jobportal.user.domain.EducationProfile;
import com.jobportal.user.domain.ExperienceProfile;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.service.ExperienceProfileService;

@Service
public class ExperienceProfileServiceImpl implements ExperienceProfileService{
	
	@Autowired
	private ExperienceProfileDAO experienceProfileDAO;

	@Override
	public List<EducationProfile> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExperienceProfile findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		experienceProfileDAO.deleteById(id);
	}

	@Override
	public ExperienceProfile saveExperienceProfile(ExperienceProfile experienceProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeExperienceProfile(ExperienceProfile experienceProfile) {
		// TODO Auto-generated method stub
		
	}

}
