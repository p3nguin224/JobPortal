package com.jobportal.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.SkillDAO;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	private SkillDAO skillDAO;

	@Override
	public Skill findBySkillName(String skillName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Skill saveSkill(Skill skill) {
		// TODO Auto-generated method stub
		return skillDAO.save(skill);
	}

	@Override
	public void removeSkill(Skill skill) {
		// TODO Auto-generated method stub
		
	}

}
