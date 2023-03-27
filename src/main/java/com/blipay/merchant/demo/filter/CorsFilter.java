package com.blipay.merchant.demo.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器
 **/
@Component
@WebFilter(urlPatterns = {"/*"}, filterName = "corsFilter")
public class CorsFilter implements Filter {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String origin = httpServletRequest.getHeader("Origin");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!StringUtils.isEmpty(origin)) {
            String corsAllowHeaders = httpServletRequest.getHeader("Access-Control-Request-Headers");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", corsAllowHeaders);
            httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(httpServletRequest.getMethod())) {
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
