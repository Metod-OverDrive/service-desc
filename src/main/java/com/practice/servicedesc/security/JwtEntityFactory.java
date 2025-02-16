package com.practice.servicedesc.security;

import com.practice.servicedesc.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }
}
