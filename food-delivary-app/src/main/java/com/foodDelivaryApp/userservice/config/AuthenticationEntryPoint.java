//package com.foodDelivaryApp.userservice.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Service
//public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        PrintWriter writer = response.getWriter();
//        writer.println("access denied ! | " + authException.getMessage());
//    }
//}
