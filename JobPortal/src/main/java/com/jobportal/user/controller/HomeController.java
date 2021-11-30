package com.jobportal.user.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.EducationProfile;
import com.jobportal.user.domain.ExperienceProfile;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.Skill;
import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.PasswordResetToken;
import com.jobportal.user.domain.security.Role;
import com.jobportal.user.domain.security.UserRole;
import com.jobportal.user.service.CompanyProfileService;
import com.jobportal.user.service.EducationProfileService;
import com.jobportal.user.service.ExperienceProfileService;
import com.jobportal.user.service.JobSeekerProfileService;
import com.jobportal.user.service.JobService;
import com.jobportal.user.service.SkillService;
import com.jobportal.user.service.UserService;
import com.jobportal.user.service.impl.UserSecurityService;
import com.jobportal.user.utility.MailConstructor;
import com.jobportal.user.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CompanyProfileService companyService;
	
	@Autowired
	private SkillService skillService;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private CompanyProfileService companyProfileService;
	
	
	private static Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping("/")
	private String homePage() {
		return "index";
	}
	
	@RequestMapping("/index")
	private String index() {
		return "redirect:/";  // redirect to home page if it comes with index
	}
	
	@RequestMapping("/jobListing")
	private String jobList(Model model,@ModelAttribute("job") Job job) {
		List<Job> jobList = jobService.findAllJobs();
		if(jobList.size() == 0 ) {
			model.addAttribute("emptyList", true);
			model.addAttribute("activeAll", true);  //
		}
		model.addAttribute("jobList", jobList);
		return "jobListing";
	}
	
	
	
	
	@RequestMapping("/about")
	private String about() {
		return "about";
	}
	
	@RequestMapping("/contact")
	private String contact() {
		return "contact";
	}
	
	@RequestMapping("/login")
	private String login(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		
		model.addAttribute("classActiveLogin", true);
		return "createNewSeeker";	
	}
	
	
	// Change success page according to role
	@RequestMapping("/success")
	private String seekerHome(HttpServletRequest request, Principal principal, Model model) {
		if (request.isUserInRole("ROLE_COMPANY")) {
			User user = userService.findByUsername(principal.getName());
			CompanyProfile companyProfile = companyService.findByUser(user);
			
			model.addAttribute("user", user);                       // <- model ui var
			model.addAttribute("companyProfile", companyProfile);		// <- model ui var
			
			List<JobSeekerProfile> jobSeekerList = jobSeekerService.findAll();
			if(jobSeekerList.size() == 0 ) {
				model.addAttribute("emptyList", true);
			}
			model.addAttribute("jobSeekerListing", jobSeekerList);		
			return "jobSeekerListing"; // change here to companyHomePage
		}
		
		User user = userService.findByUsername(principal.getName());
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
		model.addAttribute("user", user);                      
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);
		
		// for firstTime login, user need to complete edit seeker profile
		if (user.getFirstTimeLogin()) {
			model.addAttribute("classActiveProfile", true);
			return "jobSeekerProfile";
		}
		
		// unless, user will see jobListing
		List<Job> jobList = jobService.findAllJobs();
		if(jobList.size() == 0 ) {
			model.addAttribute("emptyList", true);
		}
		model.addAttribute("jobList", jobList);
		model.addAttribute("activeAll",true);
		return "jobListing"; 
	}
	
	// Go to account creating page
	@RequestMapping("/newSeeker")
	private String goToNewSeeker(Model model) {
		
		User user = new User();
		JobSeekerProfile seekerProfile = new JobSeekerProfile();
		

		model.addAttribute("classActiveNewAccount", true);

		model.addAttribute("user", user);                       // <- model ui var
		model.addAttribute("seekerProfile", seekerProfile);		// <- model ui var
		return "createNewSeeker";  
	}
	
	@RequestMapping("/newCompany")
	private String goToNewCompany(Model model) {
		User user = new User();
		CompanyProfile companyProfile = new CompanyProfile();
		
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("user", user);                       // <- model ui var
		model.addAttribute("companyProfile", companyProfile);		// <- model ui var
		
		return "createNewCompany";  
	}
	
	
	
	// Creating new job seeker account
	@PostMapping("/newSeeker")
	private String newSeeker(@ModelAttribute("user") User user,
			@ModelAttribute("seekerProfile") JobSeekerProfile seekerProfile,
			Model model) {
		
		if (userService.findByUsername(user.getUsername()) != null) {
			model.addAttribute("usernameExists", true);  		// <- model ui var
			model.addAttribute("classActiveNewAccount", true);
			return "createNewSeeker";
		}
		
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExists", true);  		// <- model ui var
			model.addAttribute("classActiveNewAccount", true);
			return "createNewSeeker";
		}
		
		if (user.getPassword().isEmpty() || user.getDob().isEmpty() || user.getGender().isEmpty()
			|| user.getUsername().isEmpty() || user.getEmail().isEmpty()) {
			model.addAttribute("missingRequiredField", true);  		// <- model ui var
			model.addAttribute("classActiveNewAccount", true);

			return "createNewSeeker";
		}
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
		user.setPassword(encryptedPassword);
		
		String todayDate = LocalDate.now().toString();  // Registerd date will be added manually
		user.setRegisteredDate(todayDate);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
			
		// add job seeker profile but it's still empty
		// let user edit jobSeeker profile in his profile page
		seekerProfile.setUser(user);   				// OneToOne connect
		seekerProfile.setStatus("busy");
		
		userService.createUser(user, userRole);	
		jobSeekerService.save(seekerProfile);

		// Still required to make token and send mail
		
		model.addAttribute("classActiveLogin", true);  		// <- model ui var // assume both login and create page in same file
		
		return "createNewSeeker";  // will reach to createNewSeeker.html 
	}
	
	
	// Creating new job seeker account
	@PostMapping("/newCompany")
	private String newCompany(@ModelAttribute("user") User user,
			@ModelAttribute("companyProfile") CompanyProfile companyProfile,
			Model model) {
			
		if (userService.findByUsername(user.getUsername()) != null) {
			model.addAttribute("usernameExists", true);  		// <- model ui var
			model.addAttribute("classActiveNewAccount", true);
			return "createNewCompany";
		}
			
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExists", true);  		// <- model ui var
			model.addAttribute("classActiveNewAccount", true);
			return "createNewCompany";
		}
			
		if (user.getPassword().isEmpty() || user.getDob().isEmpty() || user.getGender().isEmpty()
			|| user.getUsername().isEmpty() || user.getEmail().isEmpty()) {
			model.addAttribute("missingRequiredField", true);  		// <- model ui var
			model.addAttribute("classActiveNewAccount", true);

			return "createNewCompany";
		}
			
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
		user.setPassword(encryptedPassword);
			
		String todayDate = LocalDate.now().toString();  // Registerd date will be added manually
		user.setRegisteredDate(todayDate);
			
		Role role = new Role();
		role.setRoleId(2);
		role.setName("ROLE_COMPANY");
			
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
			
		userService.createUser(user, userRole);
			
		// add job seeker profile but it's still empty
		// let user edit jobSeeker profile in his profile page
		companyProfile.setUser(user);   				// OneToOne connect
		companyService.save(companyProfile);

		// Still required to make token and send mail
			
		model.addAttribute("classActiveLogin", true);  		// <- model ui var // assume both login and create page in same file
			
		return "createNewCompany";  // will reach to createNewSeeker.html 
	}
	
	@RequestMapping("/forgetPassword")
	public String forgetPassword(HttpServletRequest request,
			@ModelAttribute("email") String userEmail, Model model) {
		User user = userService.findByEmail(userEmail);
		
		User dummyUser = new User();
		model.addAttribute("user", dummyUser);
		model.addAttribute("classActiveForgetPassword", true);
		
		if (user==null) {		
			model.addAttribute("emailNotExist",true);        // <- model ui
			return "createNewSeeker";    // return page when no account is found
		}
		
		String password = SecurityUtility.randomPassword();
		String encodedPassword = SecurityUtility.passwordEncoder().encode(password);
		
		user.setPassword(encodedPassword);
		userService.save(user);
		
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		
		
		String resetUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(resetUrl, request.getLocale(), token, user, password, "Account Password Reset");
		
		mailSender.send(email);
		model.addAttribute("forgetPasswordEmailSent",true); // <- model ui
	
		
		return "createNewSeeker";
	}
	
	
	@RequestMapping("/resetPassword")
	public String resetPassword(@RequestParam("token") String token, Model model, HttpServletRequest request) {
		PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
		
		if (passwordResetToken == null) {
			String message = "Invalid Token";
			model.addAttribute("message", message);
			return "single-blog";
		}
		
		User user = passwordResetToken.getUser();
		String username = user.getUsername();
		JobSeekerProfile jobSeekerProfile = jobSeekerService.findByUser(user);
		
		// auto-Login 
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if (request.isUserInRole("ROLE_COMPANY")) {
			model.addAttribute("user", user);
			model.addAttribute("classActiveEdit", true);
			return "myProfile";
		}
		model.addAttribute("user", user);                      
		model.addAttribute("jobSeekerProfile", jobSeekerProfile);

		model.addAttribute("classActiveProfile", true);
		return "jobSeekerProfile";
	}
	
	
	


		
		
		// not work yet, have to sent to user update page
		@RequestMapping("/jobDetail")
		public String jobDetail(@RequestParam("jobId") Long jobId,Model model,Principal principal) {
			if (principal != null) {

				String username = principal.getName();

				User user = userService.findByUsername(username);

				model.addAttribute("user", user);

			}
			
			Job job = jobService.findById(jobId);
			CompanyProfile company = job.getCompanyProfile();
			List<Skill> jobSkillList = skillService.findByJob(job);
			
			model.addAttribute("job", job);
			model.addAttribute("company", company);
			model.addAttribute("skillList", jobSkillList);
			
			
			List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
			model.addAttribute("qtyList", qtyList);
			model.addAttribute("qty", 1);
			
			return "jobDetail";
			
		}

		
		// Go to job creating page
		@RequestMapping("/newJob")
		private String goToNewJob(Model model) {
			
			Job job = new Job();
			model.addAttribute("classActiveNewAccount", true);

			model.addAttribute("job", job);                       // <- model ui var
				// <- model ui var
			return "newJob";  
		}
		
		
		
		
}


