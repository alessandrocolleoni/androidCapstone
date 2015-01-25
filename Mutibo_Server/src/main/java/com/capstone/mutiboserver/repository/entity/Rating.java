package com.capstone.mutiboserver.repository.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(targetEntity = com.capstone.mutiboserver.repository.entity.Set.class)
	@JoinColumn(name = "ID_SET", unique = true, nullable = false)
	private com.capstone.mutiboserver.repository.entity.Set set;
	
	private float avgRatingValue;
	
	@ElementCollection
	private Set<String> usersNameRatedSet = new HashSet<String>();
	
	@ElementCollection
	private List<Float> ratings = new ArrayList<Float>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getAvgRatingValue() {
		return avgRatingValue;
	}

	public void setAvgRatingValue(float avgRatingValue) {
		this.avgRatingValue = avgRatingValue;
	}

	public Set<String> getUsersNameRatedSet() {
		return usersNameRatedSet;
	}

	public void setUsersNameRatedSet(Set<String> usersNameRatedSet) {
		this.usersNameRatedSet = usersNameRatedSet;
	}

	public List<Float> getRatings() {
		return ratings;
	}

	public void setRatings(List<Float> ratings) {
		this.ratings = ratings;
	}

	public com.capstone.mutiboserver.repository.entity.Set getSet() {
		return set;
	}

	public void setSet(com.capstone.mutiboserver.repository.entity.Set set) {
		this.set = set;
	}
	
}
