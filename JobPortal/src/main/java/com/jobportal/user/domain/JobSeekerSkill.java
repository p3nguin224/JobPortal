package com.jobportal.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class JobSeekerSkill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long jobSeekerSkillId;
	
	@ManyToOne
	@JoinColumn(name="jobSeekerProfileId")
	private JobSeekerProfile jobSeekerProfile;
	
	@ManyToOne
	@JoinColumn(name = "skillId")
	private Skill skill;
	
	public JobSeekerSkill() {
		
	}

	public Long getJobSeekerSkillId() {
		return jobSeekerSkillId;
	}

	public void setJobSeekerSkillId(Long jobSeekerSkillId) {
		this.jobSeekerSkillId = jobSeekerSkillId;
	}

	public JobSeekerProfile getJobSeekerProfile() {
		return jobSeekerProfile;
	}

	public void setJobSeekerProfile(JobSeekerProfile jobSeekerProfile) {
		this.jobSeekerProfile = jobSeekerProfile;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	
}
