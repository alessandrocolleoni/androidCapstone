package com.capstone.mutiboserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.mutiboserver.repository.entity.Set;

@Repository
public interface SetRepository extends CrudRepository<Set, Long> {
	
	//ArrayList<Set> find
	
}
