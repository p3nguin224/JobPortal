package com.jobportal.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SeekerJobActivity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long activityId;
	
	private String applyDate;
	
	@ManyToOne
	@JoinColumn(name = "jobId")
	private Job job;
	
	@ManyToOne
	@JoinColumn(name = "jobSeekerProfileId")
	private JobSeekerProfile jobSeekerProfile;
	
	public SeekerJobActivity() {
		
	}
	
	

	public SeekerJobActivity(Job job, JobSeekerProfile jobSeekerProfile) {
		this.job = job;
		this.jobSeekerProfile = jobSeekerProfile;
	}



	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public JobSeekerProfile getJobSeekerProfile() {
		return jobSeekerProfile;
	}

	public void setJobSeekerProfile(JobSeekerProfile jobSeekerProfile) {
		this.jobSeekerProfile = jobSeekerProfile;
	}

}
