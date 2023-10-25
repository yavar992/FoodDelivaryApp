package com.foodDelivaryApp.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableMethodSecurity
public class SpringSecurity {

    private static final String[] PUBLIC_URL = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception->exception.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth->auth.requestMatchers(PUBLIC_URL).permitAll()
                        .requestMatchers("/api/v1/users/**").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/restaurant/**").hasAnyRole("USER","RESTAURANTS_OWNER")
                        .requestMatchers("/api/v1/restaurant/**").hasAnyRole("RESTAURANTS_OWNER")
                        .requestMatchers(HttpMethod.GET,"api/v1/restaurants/menu/Items/**").hasAnyRole("USER","RESTAURANTS_OWNER")
                        .requestMatchers("api/v1/restaurants/menu/Items/**").hasAnyRole("RESTAURANTS_OWNER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/restaurants/menu/**").hasAnyRole("USER","RESTAURANTS_OWNER")
                        .requestMatchers("/api/v1/restaurants/menu/**").hasAnyRole("RESTAURANTS_OWNER")
                        .requestMatchers("*").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
