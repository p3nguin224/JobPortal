package com.jobportal.user.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.EducationProfile;
import com.jobportal.user.domain.ExperienceProfile;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.JobSeekerSkill;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.domain.SkillHolder;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.CompanyProfileService;
import com.jobportal.user.service.EducationProfileService;
import com.jobportal.user.service.ExperienceProfileService;
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
	private JobService jobService;
	
	@Autowired
	private CompanyProfileService companyService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	@Autowired
	private EducationProfileService educationProfileService;
	
	@Autowired
	private ExperienceProfileService experienceProfileService;
	
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
		
		EducationProfile educationProfile = new EducationProfile();
		ExperienceProfile experienceProfile = new ExperienceProfile();
		
		model.addAttribute("educationProfile", educationProfile);
		model.addAttribute("experienceProfile", experienceProfile);
		
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
			if ((userService.findByEmail(user.getEmail())).getUserId() != currentUser.getUserId()) {
				LOG.info("Email exists");
				model.addAttribute("emailExists", true);
				model.addAttribute("user", user);
				model.addAttribute("jobSeekerProfile", jobSeekerProfile);
				model.addAttribute("classActiveProfile", true);
				
				EducationProfile educationProfile = new EducationProfile();
				ExperienceProfile experienceProfile = new ExperienceProfile();
				
				model.addAttribute("educationProfile", educationProfile);
				model.addAttribute("experienceProfile", experienceProfile);
				return "jobSeekerProfile";
			}
		}
		
		if (userService.findByUsername(user.getUsername()) != null) {
			if ((userService.findByUsername(user.getUsername())).getUserId() != currentUser.getUserId()){
				LOG.info("Username exists");
				model.addAttribute("usernameExists", true);
				model.addAttribute("user", user);
				model.addAttribute("jobSeekerProfile", jobSeekerProfile);
				model.addAttribute("classActiveProfile", true);
				
				EducationProfile educationProfile = new EducationProfile();
				ExperienceProfile experienceProfile = new ExperienceProfile();
				
				model.addAttribute("educationProfile", educationProfile);
				model.addAttribute("experienceProfile", experienceProfile);
				return "jobSeekerProfile";
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
				model.addAttribute("user", user);
				model.addAttribute("jobSeekerProfile", jobSeekerProfile);
				model.addAttribute("classActiveProfile", true);
				
				EducationProfile educationProfile = new EducationProfile();
				ExperienceProfile experienceProfile = new ExperienceProfile();
				
				model.addAttribute("educationProfile", educationProfile);
				model.addAttribute("experienceProfile", experienceProfile);
				return "jobSeekerProfile";
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
		
		MultipartFile profileImage = jobSeekerProfile.getProfileImage();
		if (!profileImage.isEmpty()) {
			byte[] pbytes = profileImage.getBytes();
			String fileName = "jobSeekerProfile"+jobSeekerProfile.getSeekerProfileId()+".png";
			
			try {
				Files.delete(Paths.get("src/main/resources/static/image/jobSeeker/"+fileName));
			} catch (NoSuchFileException e) {
				// TODO: handle exception
			}		
			
			BufferedOutputStream profileStream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/jobSeeker/"+fileName))
					);
			profileStream.write(pbytes);
			profileStream.close();
		}
		
		
		model.addAttribute("updateUserInfo", true);
		model.addAttribute("user", user);
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		model.addAttribute("classActiveProfile", true);
		
		EducationProfile educationProfile = new EducationProfile();
		ExperienceProfile experienceProfile = new ExperienceProfile();
		
		model.addAttribute("educationProfile", educationProfile);
		model.addAttribute("experienceProfile", experienceProfile);
		return "jobSeekerProfile";   
	}
	
	
	@RequestMapping("/applyJobConfirmation")
	private String applyJobConfirmation(Principal principal, Model model,
			@RequestParam("jobId") Long jobId, @RequestParam("companyId") Long companyId) {
		
		String username = principal.getName();
		User user = userService.findByUsername(username);
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		Job job = jobService.findById(jobId);
		CompanyProfile companyProfile = companyService.findById(companyId);
		
		// to user
		mailSender.send(mailConstructor.constructApplyJobComfirmationEmail(user, jobSeekerProfile, companyProfile, job, Locale.ENGLISH));
		
		// to comapny
		mailSender.send(mailConstructor.constructApplyJobNotificationEmail(user, jobSeekerProfile, companyProfile, job, Locale.ENGLISH));
		jobSeekerService.applyJob(jobSeekerProfile, job);
		
		return "redirect:/jobListing";
	}
	
	@PostMapping("/updateEducationInfo")
	private String addEducationProfile(@ModelAttribute("educationProfile") EducationProfile educationProfile,
			 Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
	//	educationProfileService.saveEductationProfile(educationProfile);
		if (jobSeekerProfile.getEducationProfileList()==null) {
			List<EducationProfile> tempEduList = new ArrayList<EducationProfile>();
			jobSeekerProfile.setEducationProfileList(tempEduList);
			jobSeekerProfile.getEducationProfileList().add(educationProfile);
		}else {
			jobSeekerProfile.getEducationProfileList().add(educationProfile);
		}
		
		educationProfile.setJobSeekerProfile(jobSeekerProfile);
		jobSeekerService.save(jobSeekerProfile);
		
		model.addAttribute("user", user);                      
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		
		model.addAttribute("classActiveEducation", true);
		EducationProfile newEducationProfile = new EducationProfile();
		ExperienceProfile newExperienceProfile = new ExperienceProfile();
		
		model.addAttribute("educationProfile", newEducationProfile);
		model.addAttribute("experienceProfile", newExperienceProfile);
		model.addAttribute("updateEducationInfo", true);
		return "jobSeekerProfile";
	}
	
	@RequestMapping("/removeEducationProfile")
	private String removeEducationProfile(@RequestParam("educationProfileId") Long educationId,
			Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
		for (EducationProfile edu : jobSeekerProfile.getEducationProfileList()) {
			if (edu.getEducationId() == educationId) {
				edu.setJobSeekerProfile(null);
			}
		}
		educationProfileService.removeById(educationId);
		jobSeekerService.save(jobSeekerProfile);
		
		model.addAttribute("user", user);                      
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		
		model.addAttribute("classActiveEducation", true);
		EducationProfile newEducationProfile = new EducationProfile();
		ExperienceProfile newExperienceProfile = new ExperienceProfile();
		
		model.addAttribute("educationProfile", newEducationProfile);
		model.addAttribute("experienceProfile", newExperienceProfile);
		
		return "jobSeekerProfile";
	}
	
	@PostMapping("/updateExperienceInfo")
	private String updateExperienceInfo(@ModelAttribute("experienceProfile") ExperienceProfile experienceProfile,
			 Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
	//	educationProfileService.saveEductationProfile(educationProfile);
		if (jobSeekerProfile.getExperienceProfileList()==null) {
			List<ExperienceProfile> tempExpList = new ArrayList<ExperienceProfile>();
			jobSeekerProfile.setExperienceProfileList(tempExpList);
			jobSeekerProfile.getExperienceProfileList().add(experienceProfile);
		}else {
			jobSeekerProfile.getExperienceProfileList().add(experienceProfile);
		}
		
		experienceProfile.setJobSeekerProfile(jobSeekerProfile);
		jobSeekerService.save(jobSeekerProfile);
		
		model.addAttribute("user", user);                      
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		
		model.addAttribute("classActiveExperience", true);
		EducationProfile newEducationProfile = new EducationProfile();
		ExperienceProfile newExperienceProfile = new ExperienceProfile();
		
		model.addAttribute("educationProfile", newEducationProfile);
		model.addAttribute("experienceProfile", newExperienceProfile);
		model.addAttribute("updateExperienceInfo", true);
		return "jobSeekerProfile";
	}
	
	@RequestMapping("/removeExperienceProfile")
	private String removeExperienceProfile(@RequestParam("experienceProfileId") Long experienceId,
			Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
		for (ExperienceProfile exp : jobSeekerProfile.getExperienceProfileList()) {
			if (exp.getExperienceId() == experienceId) {
				exp.setJobSeekerProfile(null);
			}
		}
		experienceProfileService.removeById(experienceId);
		jobSeekerService.save(jobSeekerProfile);
		
		model.addAttribute("user", user);                      
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		
		model.addAttribute("classActiveExperience", true);
		EducationProfile newEducationProfile = new EducationProfile();
		ExperienceProfile newExperienceProfile = new ExperienceProfile();
		
		model.addAttribute("educationProfile", newEducationProfile);
		model.addAttribute("experienceProfile", newExperienceProfile);
		
		return "jobSeekerProfile";
	}

}
