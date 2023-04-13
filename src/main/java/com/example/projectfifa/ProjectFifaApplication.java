package com.example.projectfifa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProjectFifaApplication {


	// when starting a project spring scans all classes. Spring sees this class and sees this annotation @Bean.
	// This method creates new object BCryptPasswordEncoder, and saves it in its container.
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectFifaApplication.class, args);
	}

}
