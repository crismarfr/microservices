package com.example.demo;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

@Override
public void configure(HttpSecurity http) throws Exception {
	http
	.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests().anyRequest().permitAll();
		
}

}
