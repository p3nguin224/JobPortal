package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.Job;

public interface JobDAO extends CrudRepository<Job, Long>{

}
