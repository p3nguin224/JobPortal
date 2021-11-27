package com.jobportal.user.service;

import com.jobportal.user.domain.Skill;

public interface SkillService {
	
	Skill findBySkillName(String skillName);
	
	Skill saveSkill(Skill skill);
	
	void removeSkill(Skill skill);
	
	

}
