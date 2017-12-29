package com.example.demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.Message;

@Component
@FeignClient(name="microservice1-client", url="http://localhost:8090", fallback=AppelClientFallBack.class)
public interface AppelClient {	
	@RequestMapping(value="/sayHello", method=RequestMethod.GET)
	public Message getMessage();
}
