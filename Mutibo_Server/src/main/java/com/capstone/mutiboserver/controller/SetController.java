package com.capstone.mutiboserver.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.mutiboserver.client.MutiboApi;
import com.capstone.mutiboserver.repository.SetRepository;
import com.capstone.mutiboserver.repository.entity.Set;
import com.google.common.collect.Lists;

@Controller
public class SetController {
	
	@Autowired
	private SetRepository setRepository;
	
	@RequestMapping(value = MutiboApi.SET_PATH + "/list", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Set> getListSet() {
		return Lists.newArrayList(setRepository.findAll());
	}
	
	@RequestMapping(value = MutiboApi.SET_PATH + "/create", method = RequestMethod.POST)
	public @ResponseBody Set addSet(@RequestBody Set newSet, Principal p) {
		// add username in the explanation
		newSet.setExplanation(newSet.getExplanation() + " (" + p.getName() + ")");
		return setRepository.save(newSet);
	}
	
	@RequestMapping(value = MutiboApi.SET_PATH + "/delete/{setId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteSet(@PathVariable("setId") long setId) {
		setRepository.delete(setId);
	}
	
}
