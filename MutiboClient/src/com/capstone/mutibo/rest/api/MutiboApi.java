package com.capstone.mutibo.rest.api;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import com.capstone.mutibo.auth.MutiboUser;
import com.capstone.mutibo.gameObjects.Rating;
import com.capstone.mutibo.gameObjects.Set;

public interface MutiboApi {

	public static final String PASSWORD_PARAMETER = "password";

	public static final String USERNAME_PARAMETER = "username";
	
	public static final String TOKEN_PATH = "/oauth/token";
	
	// The path of Sets
	public static final String SET_PATH = "/set";

	// The path to get all the Sets
	public static final String SET_LIST_PATH = SET_PATH + "/list";
	
	// The path to add a set to the repository
	public static final String SET_CREATE_PATH = SET_PATH + "/create";
	
	// The path of Ratings
	public static final String RATING_PATH = "/rating";
	
	// The path of users
	public final static String USER_PATH = "/user";
	
	
	/* Set API */
	@GET(value = SET_PATH + "/list")
	public ArrayList<Set> getListSet();
	
	@POST(value = SET_PATH + "/create")
	public Set addSet(@Body Set newSet);
	
	@DELETE(value = SET_PATH + "/delete/{setId}")
	public Void deleteSet(@Path("setId") long ratingId);
	
	
	/* Rating API */
	@GET(value = RATING_PATH + "/list")
	public ArrayList<Rating> getAllRatings();
	
	@POST(value = RATING_PATH + "/rate/{ratingValue}/")
	public Rating rateSet(@Body Set setToRate, @Path("ratingValue") float ratingValue);
	
	@DELETE(value = RATING_PATH + "/delete/{ratingId}")
	public Void deleteRating(@Path("ratingId") long ratingId);
	
	/* User API */
	@GET(value = USER_PATH + "/findByUsername")
	public MutiboUser getUser(@Query("username") String username);
	
	/* TODO:
	@POST(value = USER_PATH + "/createUser")
	public MutiboUser addUser(@Body MutiboUser newUser);
	*/
	
}
