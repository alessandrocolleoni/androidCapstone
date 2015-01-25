package com.capstone.mutiboserver.auth;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capstone.mutiboserver.repository.MutiboUserRepository;
import com.capstone.mutiboserver.repository.entity.MutiboUser;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	MutiboUserRepository repo;
	
	public CustomUserDetailService() {super();}
	
	public CustomUserDetailService(MutiboUserRepository mur) {
		super();
		repo = mur;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		MutiboUser user = repo.findByUsername(username);
		return new User(user.getUsername(), user.getPassword(),AuthorityUtils.createAuthorityList(user.getRole()) );// new User(username, "password", true, true, true, true, new GrantedAuthority[]{ new GrantedAuthorityImpl("ROLE_USER") });
		 
	}

}
