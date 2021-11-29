package com.jobportal.user.domain.security;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.jobportal.user.domain.User;

@Entity
public class PasswordResetToken {
	
private static final int EXPIRATION = 60;
	
	// One sided relation... User can't be access Token, but Token can use User
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	private Date expiryDate;
	
	@OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name="userId") // user can't be null as token exist coz of user
	private User user;
	
	public PasswordResetToken() {
		
	}

	public PasswordResetToken(String token, User user) {
		super();
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
		this.user = user;
	}

	private Date calculateExpiryDate(int expiryTimeInMinute) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinute);
		return new Date(cal.getTime().getTime());
	}
	
	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}
	
	


}
