package com.jobportal.user.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.JobSeekerProfileService;
import com.jobportal.user.service.UserService;

@Controller
@RequestMapping("/jobSeeker")
public class JobSeekerController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	// prepare and go to profile page
	@RequestMapping("/profile")
	private String prepareJobSeekerProfile(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		
		// show experience proflie list if this profile have experience profile
		if (jobSeekerProfile.getExperienceProfileList().size()>0) {
			model.addAttribute("showExperienceProfile", true);
		}
		
		if (jobSeekerProfile.getEducationProfileList().size()>0) {
			model.addAttribute("showEducationProfile", true);
		}
		
		return "jobSeekerProfile";
	}
	
	@PostMapping("/profile")
	private String updateJobSeekerProfile(@ModelAttribute("jobSeekerProfile") JobSeekerProfile jobSeekerProfile) throws IOException {
		jobSeekerService.save(jobSeekerProfile);
		
		MultipartFile profileImage = jobSeekerProfile.getProfileImage();
		byte[] bytes = profileImage.getBytes();
		
		String fileName = "seekerProfile"+jobSeekerProfile.getSeekerProfileId()+".png";
		BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File("src/main/resources/static/image/jobSeeker/"+fileName))
				);
		stream.write(bytes);
		stream.close();
 		
		return "redirect:/profile";   
	}

}
