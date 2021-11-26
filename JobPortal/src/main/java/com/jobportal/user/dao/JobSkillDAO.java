package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.JobSkill;

public interface JobSkillDAO extends CrudRepository<JobSkill, Long>{

}
