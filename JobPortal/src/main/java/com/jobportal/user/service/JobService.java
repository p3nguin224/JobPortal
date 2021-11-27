package com.jobportal.user.service;

import java.util.List;

import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSkill;

public interface JobService {
	Job findById(Long id);
	
	Job saveJob(Job job);
	
	void removeJob(Job job);
	
	Job createJob(Job job, List<JobSkill> jobSkillList);
	
	List<Job> findAllJobs();

}
