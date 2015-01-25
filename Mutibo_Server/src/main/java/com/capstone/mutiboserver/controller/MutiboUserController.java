package com.capstone.mutiboserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.mutiboserver.client.MutiboApi;
import com.capstone.mutiboserver.repository.MutiboUserRepository;
import com.capstone.mutiboserver.repository.entity.MutiboUser;

@Controller
public class MutiboUserController {
	
	@Autowired
	private MutiboUserRepository repository;
	
	// 
	@RequestMapping(value = MutiboApi.USER_PATH + "/findByUsername", method = RequestMethod.GET)
	public @ResponseBody MutiboUser getUser(@RequestParam("username") String userName) {
		return repository.findByUsername(userName);
	}
	
	/*
	@RequestMapping(value = MutiboApi.USER_PATH + "/createUser", method = RequestMethod.POST)
	public @ResponseBody MutiboUser addUser(@RequestBody MutiboUser newUser) {
		
		return repository.save(newUser);
	}
	*/
	
	
}
