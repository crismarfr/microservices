package com.example.demo.gateway;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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


        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        log.info(" RESPONSE HTTP {} ", response.getStatus()+"---"+ response.getHeader("Authorization")+"---"+response.getContentType());
        
        return null;

    }

}