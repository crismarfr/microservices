package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.bean.Message;
import com.example.feign.OAuth2FeignAutoConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Import(OAuth2FeignAutoConfiguration.class)
@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableFeignClients
@EnableHystrix
public class DemoApplication6{
	

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PreAuthorize("#oauth2.hasScope('microservice6')")
	@RequestMapping(value="/callMicroservice", method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "defaultCallMicroservice")
	public Message callMicroservice(){
		Message mess=new Message();
		mess.setMess("Status OK microservice6 !");
		
		log.info(mess.getMess());
		return mess;
	}
	
	private Message defaultCallMicroservice() {
		 Message mess=new Message();
			mess.setMess("Mode dégradé : Status OK microservice6 !");
			
			log.info(mess.getMess());
			return mess;
	}
	
	
	static AppelClient appelClient;
	
	@RequestMapping(value="/callMicroservice1", method=RequestMethod.GET)
	private Message appelMicroservice1() {
		Message mess=new Message();
		mess.setMess(callMicroservice().getMess() +" --- "+ appelClient.getMessage().getMess());
		
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
		
		ConfigurableApplicationContext context=SpringApplication.run(DemoApplication6.class, args);	
		appelClient=context.getBean(AppelClient.class);
		
		SpringApplication.run(DemoApplication6.class, args);
	}

}
