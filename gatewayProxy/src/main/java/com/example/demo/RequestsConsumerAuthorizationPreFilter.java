package com.example.demo;

import com.example.demo.bean.AuthResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;


@Component
public class RequestsConsumerAuthorizationPreFilter extends ZuulFilter {


    Logger log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper mapper= new ObjectMapper();
    
    @Value("${security.oauth2.client.client-id}")
	private String clientId;
    
    @Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
    
    private Base64.Encoder encoder = Base64.getEncoder();  
	
	@Value("${security.oauth2.client.grant_type}")
	private String grantType;
	
	@Value("${spring.security.user.name}")
	private String username;
	
	@Value("${spring.security.user.password}")
	private String password;
	
	
    @Autowired(required=true)
    private AppelAuth appelAuth;

    @Override

    public String filterType() {

        return "pre";

    }


    @Override

    public int filterOrder() {

        return 1;

    }


    @Override

    public boolean shouldFilter() {

        return true;

    }


    @Override

    public Object run() throws ZuulException {

    	AuthResponse response=null;
    	String authorization="";
    	try
    	{
    		String tokenString = clientId +':'+ clientSecret;
    		authorization="Basic "+ encoder.encodeToString(tokenString.getBytes());
    		response=appelAuth.appelAuth(authorization,grantType,username,password);
    	}
    	catch (Exception e)
    	{
    		log.error(" ERROR AUTHENTIFICATION RESPONSE HTTP = ", e.getMessage()+" --- STATUS = "+ e.getCause()+" --- AUTHORISATION ="+ authorization);
    	}
    	
    	log.info("AUTHENTIFICATION OK RESPONSE TOKEN = ", response.getAccess_token());
    	
    	if(response.getAccess_token() != null)
    	{
	        RequestContext ctx = RequestContext.getCurrentContext();
	        ctx.getZuulRequestHeaders().put(HttpHeaders.AUTHORIZATION, "Bearer "+ response.getAccess_token());
    	}
        return null;

    }

}