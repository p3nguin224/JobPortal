package com.jobportal.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class JobSkill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long jobSkillId;
	
	@ManyToOne
	@JoinColumn(name = "skillId")
	private Skill skill;
	
	@ManyToOne
	@JoinColumn(name = "jobId")
	private Job job;
	
	public JobSkill() {
		
	}

	public Long getJobSkillId() {
		return jobSkillId;
	}

	public void setJobSkillId(Long jobSkillId) {
		this.jobSkillId = jobSkillId;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	

}
