package com.jobportal.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
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
	
	

}
