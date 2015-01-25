package com.capstone.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;

import com.capstone.mutiboserver.client.MutiboApi;
import com.capstone.mutiboserver.client.MutiboRestBuilder;
import com.capstone.mutiboserver.client.MutiboRestException;
import com.capstone.mutiboserver.repository.entity.MutiboUser;
import com.capstone.mutiboserver.repository.entity.Rating;
import com.capstone.mutiboserver.repository.entity.Set;
import com.capstone.test.data.UnsafeHttpsClient;

public class TestClass {

	private final MutiboApi validClient = new MutiboRestBuilder()
			.setLoginEndpoint("https://localhost:8443/oauth/token")
			.setUsername("user0")
			.setPassword("pass")
			.setClientId("mobile")
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint("https://localhost:8443").setLogLevel(LogLevel.FULL)
			.build().create(MutiboApi.class);

	private final MutiboApi invalidClient = new MutiboRestBuilder()
			.setLoginEndpoint("https://localhost:8443/oauth/token")
			.setUsername("userXXXXXXXX")
			.setPassword("pass")
			.setClientId("mobile")
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint("https://localhost:8443").setLogLevel(LogLevel.FULL)
			.build().create(MutiboApi.class);

	@Test
	public void testCorrect() {

		MutiboUser mutiboUser = validClient.getUser("user0");
		assertTrue(mutiboUser != null);

		Set newSet = new Set();
		newSet.setAnswer1("answer1");
		newSet.setAnswer2("answer2");
		newSet.setAnswer3("answer3");
		newSet.setCorrectAnswer("correctAnswer");
		newSet.setExplanation("explanation");
		newSet.setQuestion("question");

		Set createdSet = validClient.addSet(newSet);

		boolean found = false;
		Collection<Set> sets = validClient.getListSet();
		for (Set set : sets) {
			if (set.getId() == createdSet.getId()) {
				found = true;
				break;
			}
		}
		assertTrue(found);

		found = false;
		Rating createdRating = validClient.rateSet(createdSet, 3);
		Collection<Rating> ratings = validClient.getAllRatings();
		for (Rating rating : ratings) {
			if (rating.getId() == createdRating.getId()) {
				found = true;
				break;
			}
		}
		assertTrue(found);

		// delete
		found = false;
		validClient.deleteRating(createdRating.getId());
		ratings = validClient.getAllRatings();
		for (Rating rating : ratings) {
			if (rating.getId() == createdRating.getId()) {
				found = true;
				break;
			}
		}
		assertTrue(!found);

		found = false;
		validClient.deleteSet(createdSet.getId());
		sets = validClient.getListSet();
		for (Set set : sets) {
			if (set.getId() == createdSet.getId()) {
				found = true;
				break;
			}
		}
		assertTrue(!found);

	}

	@Test
	public void testAccessDeniedWithIncorrectCredentials() throws Exception {

		try {
			invalidClient.getUser("user0");
			fail("The server should have prevented the client from retrieving a user.");
		} catch (RetrofitError e) {
			assert (e.getCause() instanceof MutiboRestException);
		}
	}

}
