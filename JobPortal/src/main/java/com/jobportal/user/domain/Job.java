package com.jobportal.user.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long jobId;
	
	private String jobTitle;
	private String category;
	private Integer minBudget;
	private Integer maxBudget;
	private Integer requiredMember;
	private String location;
	private String lastAppliedDate;
	private String deadlineDate;
	private String requiredLanguage;
	private String status;
	private String postedDate;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<JobSkill> jobSkillList;
	
	@ManyToOne
	@JoinColumn(name = "companyProfileId")
	private CompanyProfile companyProfile;
	
	public Job() {
		
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public Integer getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(Integer minBudget) {
		this.minBudget = minBudget;
	}

	public Integer getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(Integer maxBudget) {
		this.maxBudget = maxBudget;
	}

	public Integer getRequiredMember() {
		return requiredMember;
	}

	public void setRequiredMember(Integer requiredMember) {
		this.requiredMember = requiredMember;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLastAppliedDate() {
		return lastAppliedDate;
	}

	public void setLastAppliedDate(String lastAppliedDate) {
		this.lastAppliedDate = lastAppliedDate;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getRequiredLanguage() {
		return requiredLanguage;
	}

	public void setRequiredLanguage(String requiredLanguage) {
		this.requiredLanguage = requiredLanguage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<JobSkill> getJobSkillList() {
		return jobSkillList;
	}

	public void setJobSkillList(List<JobSkill> jobSkillList) {
		this.jobSkillList = jobSkillList;
	}

	public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}
	
	
	

}
