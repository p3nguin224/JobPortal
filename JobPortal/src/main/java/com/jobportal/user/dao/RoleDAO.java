package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.security.Role;

public interface RoleDAO extends CrudRepository<Role, Integer>{

	Role findByName(String name);
}
