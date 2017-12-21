package com.example.demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.bean.Message;

@Component
@FeignClient(name="microservice2-client", url="http://${gateway.hostname}:${gateway.port}/api2", fallback=AppelClientFallBack.class)
public interface AppelClient {	
	@RequestMapping(value="/callMicroservice", method=RequestMethod.GET)
	public Message getMessage();
}
