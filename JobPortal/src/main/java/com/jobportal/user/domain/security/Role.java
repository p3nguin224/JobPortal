package com.jobportal.user.domain.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.jobportal.user.domain.User;

@Entity
public class Role {
	
	@Id
	private Integer roleId;
	
	private String name;
	
	@OneToMany(mappedBy = "role")
	private User user;
	
	public Role() {
		
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
