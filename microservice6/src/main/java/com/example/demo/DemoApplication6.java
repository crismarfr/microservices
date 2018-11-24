package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.bean.Message;


@SpringBootApplication
@RestController
@EnableFeignClients
public class DemoApplication6{

	@Autowired
	private AppelClient appelClient;
	
	@RequestMapping(value="/callMicroservice", method=RequestMethod.GET)
	public Message callMicroservice(){
		Message mess=new Message();
		mess.setMess("Status OK microservice6 !");
		return mess;
	}
	
	@RequestMapping(value="/callMicroservice2", method=RequestMethod.GET)
	 private Message appelMicroservice2() {
		
		Message mess=new Message();
		mess.setMess(/*callMicroservice().getMess() +" --- "+ */appelClient.getMessage().getMess());				
//		log.info(mess.getMess());
		return mess;
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication6.class, args);
	}

}