package com.jobportal.user.controller;

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

}
