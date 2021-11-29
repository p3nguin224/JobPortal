package com.jobportal.user.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.User;
import com.jobportal.user.service.CompanyProfileService;
import com.jobportal.user.service.UserService;

@Controller
@RequestMapping("/company")
public class CompanyController {
	
	private static Logger LOG = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyProfileService companyProfileService;
	
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
