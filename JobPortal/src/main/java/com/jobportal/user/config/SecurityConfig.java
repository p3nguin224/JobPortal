package com.jobportal.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jobportal.user.service.impl.UserSecurityService;
import com.jobportal.user.utility.SecurityUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	// AuthenticationManagerBuiler will need encoder to check
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/", 
			"/index",
			"/newUser",
			"/forgetPassword",
			"/login",
			"/fonts/**",
			"/bookshelf",
			"/bookDetail",
			"/hours",
			"/assets/**",
			"/Doc/**",
			"/job_listing",
			"/about",
			"/contact",
			"/newSeeker",
			"/newCompany"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
		
		// Security auto block server-side attack which pulls json objects
		// this security need to be block while developing state;
		http.csrf().disable().cors().disable()
		.formLogin()                  // Log in Form configuration start
		.failureUrl("/login?error")   // landing page after unsuccessful login
		.defaultSuccessUrl("/success")
		.loginPage("/login").permitAll()  // Custom login page and permit it to all users
		.and()     // login config end
		.logout()                     // Log out Form configuration start
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))   // mapping that triggers "log out" to occur.
		.logoutSuccessUrl("/?logout")  // page that will show when logout success
		.deleteCookies("remember-me").permitAll()  // delete cookies after logout
		.and()    // logout config end
		.rememberMe();	 // Browser will ask user for RememberMe option
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

}
