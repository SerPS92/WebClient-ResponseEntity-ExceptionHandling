package com.example.WebClientResponseEntityExceptionHandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class WebClientResponseEntityExceptionHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebClientResponseEntityExceptionHandlingApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

	@Bean
	public WebClient webClient(){
		return WebClient.create();
	}

}
