package com.jobportal.user.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSkill;

public interface JobSkillDAO extends CrudRepository<JobSkill, Long>{
	List<JobSkill> findByJob(Job job);

}
