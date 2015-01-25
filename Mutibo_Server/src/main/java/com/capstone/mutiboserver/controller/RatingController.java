package com.capstone.mutiboserver.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.mutiboserver.client.MutiboApi;
import com.capstone.mutiboserver.repository.RatingRepository;
import com.capstone.mutiboserver.repository.SetRepository;
import com.capstone.mutiboserver.repository.entity.Rating;
import com.google.common.collect.Lists;

@Controller
public class RatingController {

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	SetRepository setRepository;

	@RequestMapping(value = MutiboApi.RATING_PATH + "/list", method = RequestMethod.GET)
	public @ResponseBody
	ArrayList<Rating> getAllRatings() {
		return Lists.newArrayList(ratingRepository.findAll());
	}

	@RequestMapping(value = MutiboApi.RATING_PATH + "/rate/{ratingValue}/", method = RequestMethod.POST)
	public @ResponseBody
	Rating rateSet(@RequestBody com.capstone.mutiboserver.repository.entity.Set setToRate,
			@PathVariable("ratingValue") float ratingValue, Principal principal) {
		
		Rating rating = ratingRepository.findBySetId(setToRate.getId());
		
		// if a set was never rated, let's create the first rating item
		if (rating == null) {
			rating = new Rating();
			rating.setSet(setToRate);
			rating.getUsersNameRatedSet().add(principal.getName());
			rating.getRatings().add(ratingValue);
			rating.setAvgRatingValue(ratingValue);
			ratingRepository.save(rating);
		} else {

			Set<String> usersRated = rating.getUsersNameRatedSet();

			if (!usersRated.contains(principal.getName())) {
				List<Float> ratings = rating.getRatings();
				float avgRating = ratingValue;

				for (float r : ratings) {
					avgRating += r;
				}

				rating.setAvgRatingValue(avgRating /= ratings.size());
				rating.getUsersNameRatedSet().add(principal.getName());

				ratingRepository.save(rating);
			}

		}
		return rating;
	}

	@RequestMapping(value = MutiboApi.RATING_PATH + "/delete/{ratingId}", method = RequestMethod.DELETE)
	public @ResponseBody
	void deleteRatingSet(@PathVariable("ratingId") long ratingId) {
		ratingRepository.delete(ratingId);
	}

}
