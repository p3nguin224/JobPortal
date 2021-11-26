package com.jobportal.user.service;

import java.util.List;

import com.jobportal.user.domain.EducationProfile;
import com.jobportal.user.domain.JobSeekerProfile;

public interface EducationProfileService {
	List<EducationProfile> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);
	
	EducationProfile saveEductationProfile(EducationProfile educationProfile);
	
	void removeEducationProfile(EducationProfile educationProfile);
	
	EducationProfile findById(Long id);
	
	void removeById(Long id);
}
