package com.practice.servicedesc.security;

import com.practice.servicedesc.web.exception.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken!=null && bearerToken.startsWith("Bearer ")){
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken!=null && jwtProvider.validateToken(bearerToken)){
            try {
                Authentication authentication = jwtProvider.getAuthentication(bearerToken);
                if (authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (EntityNotFoundException ignore){}
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
