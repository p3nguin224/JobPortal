package com.jobportal.user.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer skillId;
	
	private String skillName;
	
	@OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<JobSeekerSkill> jobSeekerSkillList;
	
	@OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<JobSkill> jobSkillList;
	
	public Skill() {
		
	}

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public List<JobSeekerSkill> getJobSeekerSkillList() {
		return jobSeekerSkillList;
	}

	public void setJobSeekerSkillList(List<JobSeekerSkill> jobSeekerSkillList) {
		this.jobSeekerSkillList = jobSeekerSkillList;
	}

	public List<JobSkill> getJobSkillList() {
		return jobSkillList;
	}

	public void setJobSkillList(List<JobSkill> jobSkillList) {
		this.jobSkillList = jobSkillList;
	}
	
	
	
	
	

}
