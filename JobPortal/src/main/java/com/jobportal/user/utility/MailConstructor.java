package com.jobportal.user.utility;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jobportal.user.domain.CompanyProfile;
import com.jobportal.user.domain.Job;
import com.jobportal.user.domain.JobSeekerProfile;
import com.jobportal.user.domain.User;

@Component
public class MailConstructor {
	
	// its need to add to be able to use key values from application.properties
		@Autowired
		private Environment env;
		
		// We have to put context obj into template and convert that template into text
		// It can only be done by templateEngine
		@Autowired
		private TemplateEngine templateEngine;
		
		// Locale is not needed when only English is used
		// contextPath is address where to send this mail
		// This method is just constructing a message. Not send
		public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale,
				String token, User user, String password, String subject) {
			
			String url = contextPath+"/resetPassword?token="+token;
			String message = "\nPlease Click on this Link to verify your email and edit your personal information."
					+ "Your Password is : \n"+password;
			SimpleMailMessage email = new SimpleMailMessage();
			
			email.setFrom(env.getProperty("support.email"));
			email.setTo(user.getEmail());
			email.setSubject(subject);
			email.setText(url+message);
			
			return email;
		}
		
//		 MimeMessagePreparator is required to create Mime type mail
//		 It helps to put required data and create mail using these
		public MimeMessagePreparator constructApplyJobComfirmationEmail(User user, JobSeekerProfile jobSeekerProfile,
				CompanyProfile companyProfile, Job job, Locale locale) {
			Context context = new Context();
			
			context.setVariable("user", user);
			context.setVariable("jobSeekerProfile", jobSeekerProfile);
			context.setVariable("companyProfile", companyProfile);
			context.setVariable("job", job);
			
			String text = templateEngine.process("applyJobConfirmationEmailTemplate", context);
			
			MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {

				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					// TODO Auto-generated method stub
					MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
					
					email.setTo(user.getEmail());
					email.setFrom(new InternetAddress(env.getProperty("support.email")));
					email.setSubject("Job Apply - "+job.getJobTitle());
					email.setText(text, true);
				}
				
				
			};
			return messagePreparator;	
		}
		
		public MimeMessagePreparator constructApplyJobNotificationEmail(User user, JobSeekerProfile jobSeekerProfile,
				CompanyProfile companyProfile, Job job, Locale locale) {
			Context context = new Context();
			
			context.setVariable("user", user);
			context.setVariable("jobSeekerProfile", jobSeekerProfile);
			context.setVariable("companyProfile", companyProfile);
			context.setVariable("job", job);
			
			String text = templateEngine.process("applyJobConfirmationEmailTemplate", context);
			
			MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {

				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					// TODO Auto-generated method stub
					MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
					
					email.setTo(companyProfile.getCompanyEmail());
					email.setFrom(new InternetAddress(env.getProperty("support.email")));
					email.setSubject("Job Apply - "+job.getJobTitle());
					email.setText(text, true);
				}
				
			};
			return messagePreparator;	
		}

}
