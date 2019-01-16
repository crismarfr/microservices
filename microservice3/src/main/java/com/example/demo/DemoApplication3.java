package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.Message;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableHystrix
@EnableEurekaClient
@RestController
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSwagger2
public class DemoApplication3{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PreAuthorize("#oauth2.hasScope('microservice3')")
	@RequestMapping(value="/callMicroservice", method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "defaultCallMicroservice")
	public Message callMicroservice(){
		Message mess=new Message();
		mess.setMess("Status OK microservice3 !");
		
		log.info(mess.getMess());
		return mess;
	}
	
	 private Message defaultCallMicroservice() {
		 Message mess=new Message();
			mess.setMess("Mode dégradé : Status OK microservice3 !");
			
			log.info(mess.getMess());
			return mess;
	}
	
	@Autowired
	private ResourceServerProperties sso;

	@Bean
	public ResourceServerTokenServices myUserInfoTokenServices() {
		return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication3.class, args);
	}

}