package com.jobportal.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity

public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long companyId;

	private String companyName;
	private String companyPhone;
	private String companyEmail;
	private String companyAddress;
	private String companyUrl;

	private String image1;
	private String image2;

	@ManyToMany
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "jobId")
	private Job job;

	public Company() {
		// TODO Auto-generated constructor stub
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", companyPhone=" + companyPhone
				+ ", companyEmail=" + companyEmail + ", companyAddress=" + companyAddress + ", companyUrl=" + companyUrl
				+ ", image1=" + image1 + ", image2=" + image2 + ", job=" + job + "]";
	}

}
