package com.practice.servicedesc.security;

import com.practice.servicedesc.config.properties.JwtProperties;
import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.entity.enums.UserRole;
import com.practice.servicedesc.service.UserService;
import com.practice.servicedesc.web.dto.auth.JwtResponse;
import com.practice.servicedesc.web.exception.AccessDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final JwtUserDetailsService jwtEntityService;
    private final UserService userService;
    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }


    public String createAccessToken(Long userId, String username, UserRole role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id",  userId);
        claims.put("role", role.toString());
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId, String username){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserToken(String refreshToken){
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)){
            throw new AccessDeniedException("Token is not valid");
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.findById(userId);
        String username = user.getEmail();
        jwtResponse.setId(userId);
        jwtResponse.setUsername(username);
        jwtResponse.setAccessToken(createAccessToken(userId, username, user.getRole()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, username));
        return jwtResponse;
    }

    public Authentication getAuthentication(String token){
        String username = getUsername(token);
        UserDetails userDetails = jwtEntityService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }


    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    private String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
