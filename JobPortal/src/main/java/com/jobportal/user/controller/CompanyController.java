package com.jobportal.user.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.CompanyProfileService;
import com.jobportal.user.service.UserService;
import com.jobportal.user.utility.SecurityUtility;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	private static Logger LOG = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyProfileService companyProfileService;
	
	@RequestMapping("/profile")
	private String companyProfile(Principal principal, Model model){
		User user = userService.findByUsername(principal.getName());
		CompanyProfile companyProfile = companyProfileService.findByUser(user);
		model.addAttribute("user", user);                       // <- model ui var
		model.addAttribute("companyProfile", companyProfile);		// <- model ui var
		
		
		return "companyProfile";
	}
	
	
	@PostMapping("/updateUserInfo")
	private String updateUserInfo(@ModelAttribute("companyProfile") CompanyProfile companyProfile,
			@ModelAttribute("user") User user, @ModelAttribute("password") String currentPassword,
			@ModelAttribute("newPassword") String newPassword, Model model) throws Exception {
		
		User currentUser = userService.findById(user.getUserId());
		
		if (currentUser == null) {
			throw new Exception("User not Found");
		}
		
		if ((userService.findByEmail(user.getEmail()))!= null ) {
			if ((userService.findByUsername(user.getUsername())).getUserId() != currentUser.getUserId()) {
				model.addAttribute("usernameExists", true);
				return "forward:/company/profile";
			}
		}
		
		if (userService.findByUsername(user.getUsername()) != null) {
			if ((userService.findByUsername(user.getUsername())).getUserId() != currentUser.getUserId()){
				model.addAttribute("usernameExists", true);
				return "forward:/company/profile";
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
				return "forward:/company/profile";
			}
		}
		
		
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		
		currentUser.setFirstTimeLogin(false);
		
		companyProfile.setUser(currentUser);
		companyProfileService.save(companyProfile);
			
		// profileImage save
		MultipartFile profileImage = companyProfile.getImage2();
		if (!profileImage.isEmpty()) {
			byte[] pbytes = profileImage.getBytes();
			String profileName = "companyProfile"+companyProfile.getCompanyId()+".png";
			
			try {
				Files.delete(Paths.get("src/main/resources/static/image/company/profile/"+profileName));
			} catch (NoSuchFileException e) {
				// TODO: handle exception
			}		
			
			BufferedOutputStream profileStream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/company/profile/"+profileName))
					);
			profileStream.write(pbytes);
			profileStream.close();
		}
		
		
		// coverImage save
		MultipartFile coverImage = companyProfile.getImage1();
		if (!coverImage.isEmpty()) {
			byte[] cbytes = coverImage.getBytes();
			String coverName = "companyCover"+companyProfile.getCompanyId()+".png";
			
			try {
				Files.delete(Paths.get("src/main/resources/static/image/company/cover/"+coverName));
			} catch (NoSuchFileException e) {
				// TODO: handle exception
			}	
			
			BufferedOutputStream coverStream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/company/cover/"+coverName))
					);
			coverStream.write(cbytes);
			coverStream.close();
		}
		
		
		
		
		model.addAttribute("updateUserInfo", true);
		
		return "forward:/company/profile";  // temp return mapping 
	}
	
	 
	@RequestMapping("/postJob")
	private String postJob(Model model) {
		Job job = new Job();
		model.addAttribute("job", job);
		return "newJob";
	}
	
	
	@PostMapping("/postJob")
	private String creatJob(Model model, @ModelAttribute("job") Job job, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		CompanyProfile companyProfile = companyProfileService.findByUser(user);
		
		job.setStatus("avaliable");
		job.setPostedDate(LocalDate.now().toString());
		
		// only need to save company as company cascade job, but not vise vasa
		companyProfile.getJobList().add(job);
		job.setCompanyProfile(companyProfile);
		companyProfileService.save(companyProfile);
		
		return "redirect:/success"; // must be sent to company profile
	}

}
