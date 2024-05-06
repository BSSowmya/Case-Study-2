package com.javamaster.user;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.logging.Logger;


@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "User API", version = "1.0", description = "Documentation Transportation API v1.0"))
@SpringBootApplication
@EnableDiscoveryClient
public class CaseStudyUserApplication {
	private static final Logger logger = Logger.getLogger(CaseStudyUserApplication.class.getName());


	public static void main(String[] args) {
			SpringApplication.run(CaseStudyUserApplication.class, args);

	}

}
