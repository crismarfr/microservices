package com.example.demo.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestPayloadLoggingFilter extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = new HttpServletRequestWrapper(ctx.getRequest());

        log.info(String.format("%s request to %s with token %s", request.getMethod(), request.getRequestURL().toString(), request.getHeader("Authorization")));        

        String requestData = null;
        JSONParser jsonParser = new JSONParser();
        JSONObject requestJson = null;

        try {
            if (request.getContentLength() > 0 ) {
                requestData = CharStreams.toString(request.getReader());
            }

            if (requestData == null) {
                return null;
            }
            requestJson = (JSONObject) jsonParser.parse(requestData);
        } catch (Exception e) {
            log.error("Error parsing JSON request", e);
            return null;
        }
        log.info(String.format("%s request payload %s", request.getMethod(), requestJson.toJSONString()));
        return null;
    }
}
