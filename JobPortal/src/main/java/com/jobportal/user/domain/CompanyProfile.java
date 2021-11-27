package com.jobportal.user.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class CompanyProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long companyId;
	
	private String companyName;
	private String companyPhone;
	private String companyEmail;
	private String companyAddress;
	private String companyUrl;
	
	@Transient
	private MultipartFile image1;
	
	@Transient
	private MultipartFile image2;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, name="userIdFk")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Job> jobList;
	
	public CompanyProfile() {
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

	public MultipartFile getImage1() {
		return image1;
	}

	public void setImage1(MultipartFile image1) {
		this.image1 = image1;
	}

	public MultipartFile getImage2() {
		return image2;
	}

	public void setImage2(MultipartFile image2) {
		this.image2 = image2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Job> getJobList() {
		return jobList;
	}


	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	
}
