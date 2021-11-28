package com.jobportal.user.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reviewId;
	
	private String description;
	private String date;
	private String rating;
	
	@OneToOne(mappedBy = "order" ,cascade = CascadeType.ALL)
	private User user;
	
	public Review() {
		// TODO Auto-generated constructor stub
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", description=" + description + ", date=" + date + ", rating=" + rating
				+ ", user=" + user + "]";
	}
	
	


}
