package com.example.demo.annuaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
@EnableEurekaServer
public class AnnuaireApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnnuaireApplication.class, args);
	}
}
