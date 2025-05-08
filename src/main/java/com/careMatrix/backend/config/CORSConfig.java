package com.careMatrix.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
public class CORSConfig {

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public Filter corsFilter() {
//        return new Filter() {
//            @Override
//            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//                    throws IOException, ServletException {
//                HttpServletResponse response = (HttpServletResponse) res;
//                HttpServletRequest request = (HttpServletRequest) req;
//
//                response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//                response.setHeader("Access-Control-Allow-Headers", "*");
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//
//                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//                    response.setStatus(HttpServletResponse.SC_OK);
//                } else {
//                    chain.doFilter(req, res);
//                }
//            }
//        };
//    }


}
