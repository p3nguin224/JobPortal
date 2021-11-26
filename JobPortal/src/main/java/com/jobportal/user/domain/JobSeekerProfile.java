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
public class JobSeekerProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seekerProfileId;
	
	private String firstName;
	private String lastName;
	private String category;
	private String status;
	
	@Transient
	private MultipartFile profileImage;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, name="userIdFk")
	private User user;
	
	@OneToMany(mappedBy = "jobSeekerProfile", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<EducationProfile> educationProfileList;
	
	public JobSeekerProfile() {
		
	}

	public Long getSeekerProfileId() {
		return seekerProfileId;
	}

	public void setSeekerProfileId(Long seekerProfileId) {
		this.seekerProfileId = seekerProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public MultipartFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<EducationProfile> getEducationProfileList() {
		return educationProfileList;
	}

	public void setEducationProfileList(List<EducationProfile> educationProfileList) {
		this.educationProfileList = educationProfileList;
	}
	
	
	

}
