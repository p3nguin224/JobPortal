package com.jobportal.user.service;

import com.jobportal.user.domain.Job;

public interface JobService {
	Job findById(Long id);
	Job saveJob(Job job);
	void removeJob(Job job);
	Job createJob(Job job);

}
