package com.jobportal.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.JobSkillDAO;
import com.jobportal.user.dao.SkillDAO;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSkill;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService{
	
	@Autowired
	private SkillDAO skillDAO;
	
	@Autowired
	private JobSkillDAO jobSkillDAO;

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

	@Override
	public List<Skill> findByJob(Job job) {
		// TODO Auto-generated method stub
		List<Skill> skillList = new ArrayList<>();
		
		List<JobSkill> jobSkillList = jobSkillDAO.findByJob(job);
		for (JobSkill jobSkill : jobSkillList) {
			skillList.add(jobSkill.getSkill());
		}
		return skillList;
	}

	

}
