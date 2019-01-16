package com.example.demo;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Component

public class ResponsePayLoadLoggingFilter extends ZuulFilter {


    Logger log = LoggerFactory.getLogger(this.getClass());


    @Override

    public String filterType() {

        return "post";

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

    	HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        log.info(" RESPONSE HTTP {"+ response.getStatus() +"} "+ request.getRequestURL().toString());
        
        return null;

    }

}