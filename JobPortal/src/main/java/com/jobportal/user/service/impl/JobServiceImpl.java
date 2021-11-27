package com.jobportal.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.JobDAO;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSkill;
import com.jobportal.user.service.JobService;

@Service
public class JobServiceImpl implements JobService{
	
	@Autowired
	private JobDAO jobDAO;

	@Override
	public Job findById(Long id) {
		// TODO Auto-generated method stub
		return jobDAO.findById(id).get();
	}

	@Override
	public Job saveJob(Job job) {
		// TODO Auto-generated method stub
		return jobDAO.save(job);
	}

	@Override
	public void removeJob(Job job) {
		// TODO Auto-generated method stub
		jobDAO.delete(job);
	}

	@Override
	public Job createJob(Job job, List<JobSkill> jobSkillList) {
		// TODO Auto-generated method stub
		job.setJobSkillList(jobSkillList);
		return jobDAO.save(job);
	}

	@Override
	public List<Job> findAllJobs() {
		// TODO Auto-generated method stub
		return (List<Job>) jobDAO.findAll();
	}

	

}
