package com.deep.tmsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deep.tmsapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("FROM User d where d.name = :argName")
    public User findByName(@Param(value = "argName") String argName);
	
	@Query("FROM User d where d.active = true")
	public List<User> findAll();

}
