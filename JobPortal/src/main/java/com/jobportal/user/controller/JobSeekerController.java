package com.jobportal.user.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.JobSeekerProfileService;
import com.jobportal.user.service.UserService;
import com.jobportal.user.utility.SecurityUtility;

@Controller
@RequestMapping("/jobSeeker")
public class JobSeekerController {
	
	private static Logger LOG = LoggerFactory.getLogger(JobSeekerController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	// prepare and go to profile page
	@RequestMapping("/profile")
	private String prepareJobSeekerProfile(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
		model.addAttribute("user", user);
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		model.addAttribute("classActiveProfile", true);
		
		// show experience proflie list if this profile have experience profile
		if (jobSeekerProfile.getExperienceProfileList()!=null) {
			model.addAttribute("showExperienceProfile", true);
		}
		
		if (jobSeekerProfile.getEducationProfileList()!=null) {
			model.addAttribute("showEducationProfile", true);
		}
		
		return "jobSeekerProfile";
	}
	
	@PostMapping("/updateUserInfo")
	private String updateJobSeekerProfile(@ModelAttribute("jobSeekerProfile") JobSeekerProfile jobSeekerProfile,
			@ModelAttribute("user") User user, @ModelAttribute("password") String currentPassword,
			@ModelAttribute("newPassword") String newPassword,
			Model model) throws Exception {
		
		User currentUser = userService.findById(user.getUserId());
		
		if (currentUser == null) {
			throw new Exception("User not Found");
		}
		
		if ((userService.findByEmail(user.getEmail()))!= null ) {
			if ((userService.findByUsername(user.getUsername())).getUserId() != currentUser.getUserId()) {
				model.addAttribute("usernameExists", true);
				return "redirect:/jobSeeker/profile";
			}
		}
		
		if (userService.findByUsername(user.getUsername()) != null) {
			if ((userService.findByUsername(user.getUsername())).getUserId() != currentUser.getUserId()){
				model.addAttribute("usernameExists", true);
				return "redirect:/jobSeeker/profile";
			}
		}
		
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			// encrypted Password
			String dbPassword = currentUser.getPassword();
			
			// passwordEncoder.matches() checks if original password matches with encrypted password
			// if user can type in correct password which matches encrypted password in db
			// -let user change a new password

			if (passwordEncoder.matches(currentPassword, dbPassword)) {
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				LOG.info("current password : {}",currentPassword);
				LOG.info("db password : {}",dbPassword);
				model.addAttribute("incorrectPassword", true);
				return "redirect:/jobSeeker/profile";
			}
		}
		
		currentUser.setUsername(user.getUsername());
		currentUser.setDob(user.getDob());
		currentUser.setEmail(user.getEmail());
		currentUser.setGender(user.getGender());
		
		currentUser.setFirstTimeLogin(false);
		
		jobSeekerProfile.setUser(currentUser);
		jobSeekerService.save(jobSeekerProfile);
		
		
		if (jobSeekerProfile != null) {
			LOG.info("jobSEekerProfile ID"+jobSeekerProfile.getSeekerProfileId());
		}
		
//		MultipartFile profileImage = jobSeekerProfile.getProfileImage();
//		byte[] bytes = profileImage.getBytes();
//		
//		String fileName = "seekerProfile"+jobSeekerProfile.getSeekerProfileId()+".png";
//		BufferedOutputStream stream = new BufferedOutputStream(
//				new FileOutputStream(new File("src/main/resources/static/image/jobSeeker/"+fileName))
//				);
//		stream.write(bytes);
//		stream.close();
		
		model.addAttribute("updateUserInfo", true);
		return "forward:/jobSeeker/profile";   
	}

}
