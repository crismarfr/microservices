package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;  

import com.example.demo.bean.AuthResponse;

@FeignClient(name="authServer", url="http://${gateway.hostname}:${gateway.port}")
public interface AppelAuth {	
	@PostMapping(value = "/uaa/oauth/token?grant_type={grant_type}&redirect_url=&username={username}&password={password}&scope=")
	AuthResponse appelAuth(@RequestHeader("Authorization") String authHeader, @PathVariable("grant_type") String grant_type, @PathVariable("username") String username, @PathVariable("password") String password);
}