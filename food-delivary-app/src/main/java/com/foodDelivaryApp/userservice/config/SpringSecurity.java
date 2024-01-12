package com.foodDelivaryApp.userservice.config;

import com.foodDelivaryApp.userservice.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurity {

    @Autowired
    private JwtFilter jwtFilter;

    private static final String[] PUBLIC_URL = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/test/**"
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
                        .requestMatchers("/api/v1/users/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/v1/users/cart/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/v1/users/address/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/restaurant/**").hasAnyRole("USER","RESTAURANTS_OWNER")
                        .requestMatchers("/api/v1/restaurant/**").hasAnyRole("RESTAURANTS_OWNER")
                        .requestMatchers(HttpMethod.GET,"api/v1/restaurants/menu/Items/**").hasAnyRole("USER","RESTAURANTS_OWNER")
                        .requestMatchers("api/v1/restaurants/menu/Items/**").hasAnyRole("RESTAURANTS_OWNER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/restaurants/menu/**").hasAnyRole("USER","RESTAURANTS_OWNER")
                        .requestMatchers("/api/v1/restaurants/menu/**").hasAnyRole("RESTAURANTS_OWNER")
                        .requestMatchers("*").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }



    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomerUserDetailsService();
     }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }




}
