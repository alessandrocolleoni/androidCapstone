package com.capstone.mutiboserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.capstone.mutiboserver.auth.CustomUserDetailService;
import com.capstone.mutiboserver.auth.OAuth2SecurityConfiguration;
import com.capstone.mutiboserver.repository.entity.Rating;
import com.capstone.mutiboserver.repository.entity.Set;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {Rating.class, Set.class, CustomUserDetailService.class})
@EnableWebMvc
@Configuration
@ComponentScan
@Import(OAuth2SecurityConfiguration.class)
public class Application extends RepositoryRestMvcConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
}
