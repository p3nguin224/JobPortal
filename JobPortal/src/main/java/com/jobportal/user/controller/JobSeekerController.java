package com.jobportal.user.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.JobSeekerSkill;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.domain.SkillHolder;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.CompanyProfileService;
import com.jobportal.user.service.JobSeekerProfileService;
import com.jobportal.user.service.JobService;
import com.jobportal.user.service.SkillService;
import com.jobportal.user.service.UserService;
import com.jobportal.user.utility.MailConstructor;
import com.jobportal.user.utility.SecurityUtility;

@Controller
@RequestMapping("/jobSeeker")
public class JobSeekerController {
	
	private static Logger LOG = LoggerFactory.getLogger(JobSeekerController.class);
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SkillService skillService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CompanyProfileService companyService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	// prepare and go to profile page
	@RequestMapping("/profile")
	private String prepareJobSeekerProfile(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
		// Make skill List
//		SkillHolder skillHolder = new SkillHolder();
//		List<String> jobSeekerSkillNameList = new ArrayList<>();
//		if (jobSeekerProfile.getJobSeekerSkillList() != null) {
//			for (JobSeekerSkill jskill : jobSeekerProfile.getJobSeekerSkillList()) {
//				jobSeekerSkillNameList.add(jskill.getSkill().getSkillName());
//			}
//		}
//		
//		List<Skill> allSkills = skillService.findAllSkills();
//		model.addAttribute("skillHolder", skillHolder);
//		model.addAttribute("jskillNameList", jobSeekerSkillNameList);
//		model.addAttribute("allSkills", allSkills);
		/////////
		
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
			@ModelAttribute("newPassword") String newPassword, @ModelAttribute("skillHolder") SkillHolder skillHolder,
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
		
		// Take skills
//		List<Skill> skillList = new ArrayList<>();
//		for (String skillName : skillHolder.getSkills()) {
//			skillList.add(skillService.findBySkillName(skillName));
//		}
//		
//		if (jobSeekerProfile != null) {
//			LOG.info("skillNameList size "+skillHolder.getSkills().size());
//		}	
//		jobSeekerService.createJobSeekerSkillList(jobSeekerProfile, skillList);
		
		currentUser.setUsername(user.getUsername());
		currentUser.setDob(user.getDob());
		currentUser.setEmail(user.getEmail());
		currentUser.setGender(user.getGender());
		
		currentUser.setFirstTimeLogin(false);
		
		jobSeekerProfile.setUser(currentUser);
		jobSeekerService.save(jobSeekerProfile);
		

		
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
	
	
	@RequestMapping("/applyJobConfirmation")
	private String applyJobConfirmation(Principal principal, Model model,
			@RequestParam("jobId") Long jobId, @RequestParam("companyId") Long companyId) {
		
		String username = principal.getName();
		User user = userService.findByUsername(username);
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		Job job = jobService.findById(jobId);
		CompanyProfile companyProfile = companyService.findById(companyId);
		
		mailSender.send(mailConstructor.constructApplyJobComfirmationEmail(user, jobSeekerProfile, companyProfile, job, Locale.ENGLISH));
		return "redirect:/jobListing";
	}

}
