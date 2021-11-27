package com.jobportal.user.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;
import com.jobportal.user.domain.security.Role;
import com.jobportal.user.domain.security.UserRole;
import com.jobportal.user.service.JobSeekerProfileService;
import com.jobportal.user.service.UserService;
import com.jobportal.user.utility.MailConstructor;
import com.jobportal.user.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping("/")
	private String homePage() {
		return "index";
	}
	
	@RequestMapping("/index")
	private String index() {
		return "redirect:/";  // redirect to home page if it comes with index
	}
	
	@RequestMapping("/job_listing")
	private String jobList() {
		return "job_listing";
	}
	
	@RequestMapping("/about")
	private String about() {
		return "about";
	}
	
	@RequestMapping("/contact")
	private String contact() {
		return "contact";
	}
	
	// method name is not important as Controller works with mapping
	// RequestMapping will be PostMapping if client HTTP Request is differrnt
	// Model object is used to pass data to UI... Same as request.setParameter from javaEE
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
//			User user = userService.findByUsername(principal.getName());
//			JobSeekerProfile seekerProfile = jobSeekerService.findByUser(user);
//			
//			model.addAttribute("user", user);                       // <- model ui var
//			model.addAttribute("seekerProfile", seekerProfile);		// <- model ui var
			
			return "employerProfile"; // change here to companyHomePage
		}
		
		User user = userService.findByUsername(principal.getName());
		JobSeekerProfile seekerProfile = jobSeekerService.findByUser(user);
		
		model.addAttribute("user", user);                       // <- model ui var
		model.addAttribute("seekerProfile", seekerProfile);		// <- model ui var
		
		return "single-blog"; // change here to seekerHomePage
	}
	
	// Go to account creating page
	@RequestMapping("/newSeeker")
	private String goToNewSeeker(Model model) {
		
		User user = new User();
		JobSeekerProfile seekerProfile = new JobSeekerProfile();
		

		model.addAttribute("classActiveNewAccount", true);

		model.addAttribute("user", user);                       // <- model ui var
		model.addAttribute("seekerProfile", seekerProfile);		// <- model ui var
		return "createNewSeeker";  // will reach to createNewSeeker.html
	}
	
//	@RequestMapping("/newCompany")
//	private String goToNewCompany(Model model) {
//		User user = new User();
//		CompanyProfile companyProfile = new CompanyProfile();
//		
//		model.addAttribute("classActiveNewAccount", true);
//		model.addAttribute("user", user);                       // <- model ui var
//		model.addAttribute("companyProfile", companyProfile);		// <- model ui var
//		return "createNewSeeker";  // will reach to createNewSeeker.html
//	}
	
	// Creating new job seeker account
	@PostMapping("/newSeeker")
	private String newSeeker(@ModelAttribute("user") User user,
			@ModelAttribute("seekerProfile") JobSeekerProfile seekerProfile,
			Model model) {
		
		if (userService.findByUsername(user.getUsername()) != null) {
			model.addAttribute("usernameExist", true);  		// <- model ui var
			return "createNewSeeker";
		}
		
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExist", true);  		// <- model ui var
			return "createNewSeeker";
		}
		
		if (user.getPassword().isEmpty() || user.getDob().isEmpty() || user.getGender().isEmpty()
			|| user.getUsername().isEmpty() || user.getEmail().isEmpty()) {
			model.addAttribute("missingRequiredField", true);  		// <- model ui var
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
		
		userService.createUser(user, userRole);
		
		// add job seeker profile but it's still empty
		// let user edit jobSeeker profile in his profile page
		seekerProfile.setUser(user);   				// OneToOne connect
		jobSeekerService.save(seekerProfile);
		
		
		
		// Still required to make token and send mail
		
		model.addAttribute("classActiveLoginForm", true);  		// <- model ui var // assume both login and create page in same file
		
		return "createNewSeeker";  // will reach to createNewSeeker.html 
	}
	
	
	public String forgetPassword(HttpServletRequest request,
			@ModelAttribute("email") String userEmail, Model model) {
		User user = userService.findByEmail(userEmail);
		
		if (user==null) {
			model.addAttribute("emailNotExist",true);        // <- model ui
			return "myAccount";    // return page when no account is found
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
		
		return "";
	}

	
	
	// Go to account creating page
		@RequestMapping("/newCompany")
		private String goToNewCompany(Model model) {
			
			User user = new User();
			JobSeekerProfile seekerProfile = new JobSeekerProfile();
			
			model.addAttribute("classActiveNewAccount", true);		// create user acc page
			
			model.addAttribute("user", user);                       // <- model ui var
//			model.addAttribute("companyProfile", companyProfile);		// <- model ui var
			return "createNewSeeker";  // will reach to createNewSeeker.html
		}
}
