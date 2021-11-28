package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;


import com.jobportal.user.domain.Review;

public interface ReviewDAO extends CrudRepository <Review, Long>{
	
	Review findByCompanyId(Long reviewId);

}
