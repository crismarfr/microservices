package com.example.demo;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.bean.Message;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@RestController
@EnableSwagger2
public class DemoApplication5{

	@RequestMapping(value="/callMicroservice", method=RequestMethod.GET)
	public Message callMicroservice(){
		Message mess=new Message();
		mess.setMess("Status OK microservice5 !");
		return mess;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication5.class, args);
	}

}