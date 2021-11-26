package com.jobportal.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class EducationProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long educationId;
	
	private String name;
	private String major;
	private String universityName;
	private String startDate;
	private String completeDate;
	
	@Transient
	private MultipartFile profileImage;
}
