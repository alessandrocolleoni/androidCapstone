package com.capstone.mutiboserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.mutiboserver.repository.entity.MutiboUser;

@Repository
public interface MutiboUserRepository extends CrudRepository<MutiboUser, Long>{
	
	public MutiboUser findByUsername(String user);
	
}
