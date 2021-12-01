package com.jobportal.user.service;

import java.util.List;

import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.Skill;

public interface SkillService {
	
	Skill findBySkillName(String skillName);
	
	Skill saveSkill(Skill skill);
	
	void removeSkill(Skill skill);
	
	List<Skill> findByJob(Job job);
	
	List<Skill> findAllSkills();

}
