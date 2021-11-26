package com.jobportal.user.dao;

import org.springframework.data.repository.CrudRepository;

import com.jobportal.user.domain.Skill;

public interface SkillDAO extends CrudRepository<Skill, Integer>{

}
