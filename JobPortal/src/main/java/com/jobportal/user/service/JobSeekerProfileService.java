package com.jobportal.user.service;

import java.util.List;

import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.JobSeekerSkill;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.domain.User;

public interface JobSeekerProfileService {
	JobSeekerProfile findByUser(User user);
	
	JobSeekerProfile save(JobSeekerProfile jobSeekerProfile);
	
	JobSeekerProfile findById(Long id);
	
	List<JobSeekerProfile> findAll();
	
	List<JobSeekerProfile> findByCategory(String category);
	
	List<JobSeekerProfile> findAllJobSeekerByUserame(String name);
	
	JobSeekerProfile createJobSeekerSkillList(JobSeekerProfile jobSeekerProfile, List<Skill> skillList);
	
	JobSeekerProfile createJobSeekerSkill(JobSeekerProfile jobSeekerProfile, Skill skill);
	
	JobSeekerProfile applyJob(JobSeekerProfile jobSeekerProfile, Job job);

}
