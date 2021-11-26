package com.jobportal.user.controller;

import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jobportal.user.utility.SecurityUtility;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JobSeekerProfileService jobSeekerService;
	
	@RequestMapping("/")
	private String homePage() {
		return "index";
	}
	
	@RequestMapping("/index")
	private String index() {
		return "redirect:/";  // redirect to home page if it comes with index
	}
	
	// method name is not important as Controller works with mapping
	// RequestMapping will be PostMapping if client HTTP Request is differrnt
	// Model object is used to pass data to UI... Same as request.setParameter from javaEE
	@RequestMapping("/login")
	private String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "myAccount";	
	}
	
	// Change success page according to role
	@RequestMapping("/success")
	private String seekerHome(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_COMPANY")) {
			
			return "hours"; // change here to companyHomePage
		}
		return "faq"; // change here to seekerHomePage
	}
	
	// Go to account creating page
	@RequestMapping("/newSeeker")
	private String goToNewSeeker(Model model) {
		User user = new User();
		JobSeekerProfile seekerProfile = new JobSeekerProfile();
		
		model.addAttribute("user", user);                       // <- model ui var
		model.addAttribute("seekerProfile", seekerProfile);		// <- model ui var
		return "createNewSeeker";  // will reach to createNewSeeker.html
	}
	
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
	
	

}
