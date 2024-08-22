package com.student.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(e -> e.disable())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/ws/**",
                                        "/api/auth/login/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "swagger-ui.html")
                                .permitAll()
                                .requestMatchers("/api/v1/user/register").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/user/update/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/user/delete/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .exceptionHandling(e->e.authenticationEntryPoint(entryPoint))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
