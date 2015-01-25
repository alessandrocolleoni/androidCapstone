package com.capstone.mutibo.gameObjects;

import java.util.HashSet;
import java.util.Set;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {
	
	private long id;
	
	private com.capstone.mutibo.gameObjects.Set setId;
	
	private float avgRatingValue;
	
	private Set<String> usersNameRatedSet = new HashSet<String>();
	
	private Set<Double> ratings = new HashSet<Double>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public com.capstone.mutibo.gameObjects.Set getSetId() {
		return setId;
	}

	public void setSetId(com.capstone.mutibo.gameObjects.Set setId) {
		this.setId = setId;
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

	public Set<Double> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Double> ratings) {
		this.ratings = ratings;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		
	}
}
