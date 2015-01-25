package com.capstone.mutiboserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capstone.mutiboserver.repository.entity.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long>{
	
	public Rating findBySetId(long set);
	
}
