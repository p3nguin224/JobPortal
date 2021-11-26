package com.jobportal.user.service;

import java.util.List;

import com.jobportal.user.domain.EducationProfile;
import com.jobportal.user.domain.ExperienceProfile;
import com.jobportal.user.domain.JobSeekerProfile;

public interface ExperienceProfileService {
	List<EducationProfile> findByJobSeekerProfile(JobSeekerProfile jobSeekerProfile);
	
	ExperienceProfile findById(Long id);
	
	void removeById(Long id);
	
	ExperienceProfile saveExperienceProfile(ExperienceProfile experienceProfile);
	
	void removeExperienceProfile(ExperienceProfile experienceProfile);
}
