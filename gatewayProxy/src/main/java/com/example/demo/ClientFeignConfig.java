package com.example.demo;

import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
@EnableFeignClients(basePackages={"com.example.demo"})
public class ClientFeignConfig extends FeignClientsConfiguration
{
	
}
