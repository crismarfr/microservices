package com.example.demo;

//import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
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
import com.example.feign.OAuth2FeignAutoConfiguration;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@ImportAutoConfiguration(OAuth2FeignAutoConfiguration.class)
@EnableFeignClients("com.example.demo")
@EnableHystrix
@EnableEurekaClient
@RestController
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoApplication1 {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
		
	static AppelClient appelClient;
	
	@PreAuthorize("#oauth2.hasScope('microservice1')")
	@RequestMapping(value="/callMicroservice", method=RequestMethod.GET)
	public Message callMicroservice() {
		Message mess=new Message();
//		try
//		{
//		log.info("Thread start");
		
		mess.setMess("Status OK microservice1 !");
			
		log.info(mess.getMess());
		
/*		Instant future=Instant.now().plusSeconds(1);
		do {
		log.info("Thread run");		
		isInterruptedHystrix();
		}
		while (Instant.now().compareTo(future) <= 0);		
*/		
/*		}
		catch(InterruptedException ex)
		{
			log.info("Thread interrupted :"+ ex.getMessage());
		}
*/
		return mess;
	}
/*	
	private void isInterruptedHystrix() throws InterruptedException 
	{
		if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Hystrix timeOut");
        }
	}
*/	
	 private Message defaultCallMicroservice() {
		 Message mess=new Message();
			mess.setMess("Mode dégradé : Status microservice1 OK !");
			
			log.info(mess.getMess());
			return mess;
	}
	 
	@PreAuthorize("#oauth2.hasScope('microservice2')")
	@RequestMapping(value="/callMicroservice2", method=RequestMethod.GET)
	 private Message appelMicroservice2() {
		
		Message mess=new Message();
		mess.setMess(/*callMicroservice().getMess() +" --- "+ */appelClient.getMessage().getMess());				
//		log.info(mess.getMess());
		return mess;
	}
	 
	@Autowired
	private ResourceServerProperties sso;

	@Bean
	public ResourceServerTokenServices myUserInfoTokenServices() {
		return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(DemoApplication1.class, args);	
		appelClient=context.getBean(AppelClient.class);
		
		SpringApplication.run(DemoApplication1.class, args);
	}

}
