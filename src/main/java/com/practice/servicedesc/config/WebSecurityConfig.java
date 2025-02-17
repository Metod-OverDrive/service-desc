package com.practice.servicedesc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .securityMatcher("/**")
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/web/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/web/spec/**").hasAuthority("SPECIALIST")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/web/login")
                        .defaultSuccessUrl("/web/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/web/logout")
                        .logoutSuccessUrl("/web/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationManager(authenticationManager);

        return http.build();
    }
}
