package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.JobSeekerSkill;
import com.jobportal.user.domain.Skill;

public interface JobSeekerSkillDAO extends CrudRepository<JobSeekerSkill, Long>{

	JobSeekerSkill findBySkill(Skill skill);
}
