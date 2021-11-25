package com.jobportal.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class JobSeekerProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seekerProfileId;
	
	private String firstName;
	private String lastName;
	private String categroy;
	
	@Transient
	private MultipartFile profileImage;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name="userIdFk")
	private User user;
	
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

	public String getCategroy() {
		return categroy;
	}

	public void setCategroy(String categroy) {
		this.categroy = categroy;
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
	
	
	

}
