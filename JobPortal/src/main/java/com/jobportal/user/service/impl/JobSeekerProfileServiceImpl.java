package com.jobportal.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.user.dao.JobSeekerProfileDAO;
import com.jobportal.user.dao.JobSeekerSkillDAO;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.JobSeekerSkill;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.JobSeekerProfileService;
import com.jobportal.user.service.SkillService;

@Service
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService{
	
	@Autowired
	private JobSeekerProfileDAO jobSeekerDAO;
	
	@Autowired
	private JobSeekerSkillDAO jobSeekerSkillDAO;
	
	@Autowired
	private SkillService skillService;

	@Override
	public JobSeekerProfile findByUser(User user) {
		// TODO Auto-generated method stub
		return jobSeekerDAO.findByUser(user);
	}

	@Override
	public JobSeekerProfile save(JobSeekerProfile jobSeekerProfile) {
		// TODO Auto-generated method stub
		return jobSeekerDAO.save(jobSeekerProfile);
	}

	@Override
	public JobSeekerProfile findById(Long id) {
		// TODO Auto-generated method stub
		return jobSeekerDAO.findById(id).get();
	}

	@Override
	public List<JobSeekerProfile> findAll() {
		// TODO Auto-generated method stub
		List<JobSeekerProfile> jobSeekerList = ((List<JobSeekerProfile>) jobSeekerDAO.findAll()).stream().filter(jobSeeker -> jobSeeker.getStatus().equals("available")).collect(Collectors.toList());
		
		return jobSeekerList;
	}

	@Override
	public List<JobSeekerProfile> findByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public JobSeekerProfile createJobSeekerSkill(JobSeekerProfile jobSeekerProfile,
//			List<JobSeekerSkill> jobSeekerSkillList) {
//		JobSeekerProfile localjs = jobSeekerDAO.findById(jobSeekerProfile.getSeekerProfileId()).get();
//		// TODO Auto-generated method stub
//		for (JobSeekerSkill jsSkill : jobSeekerSkillList) {
//			skillService.saveSkill(jsSkill.getSkill());
//		}
//		localjs.getJobSeekerSkillList().addAll(jobSeekerSkillList);
//		return localjs;
//	}

	@Override
	public JobSeekerProfile createJobSeekerSkillList(JobSeekerProfile jobSeekerProfile, List<Skill> skillList) {
		// TODO Auto-generated method stub
		List<JobSeekerSkill> jobSeekerSkillList = new ArrayList<>();
		// It wont work as no entry in jobseekerSkill table
		for (Skill skill:skillList) {
			jobSeekerSkillList.add(jobSeekerSkillDAO.findBySkill(skill));
		}
		
		JobSeekerProfile localjs = jobSeekerDAO.findById(jobSeekerProfile.getSeekerProfileId()).get();
		
		for (JobSeekerSkill jsSkill : jobSeekerSkillList) {
			skillService.saveSkill(jsSkill.getSkill());
		}
		jobSeekerProfile.getJobSeekerSkillList().addAll(jobSeekerSkillList);
		
		localjs = jobSeekerDAO.save(jobSeekerProfile);
		
		return localjs;
	}

	@Override
	public JobSeekerProfile createJobSeekerSkill(JobSeekerProfile jobSeekerProfile, Skill skill) {
		// TODO Auto-generated method stub
		return null;
	}








	



}
