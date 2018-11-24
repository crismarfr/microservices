package com.example.demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.bean.Message;

@FeignClient(name="microservice2-client", url="http://${gateway.hostname}:${gateway.port}/api2", fallback=AppelClientFallBack.class)
public interface AppelClient {	
	@GetMapping(value="/callMicroservice")
	Message getMessage();
}
