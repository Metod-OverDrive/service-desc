package com.practice.servicedesc.service.impl;

import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.repository.UserRepository;
import com.practice.servicedesc.service.UserService;
import com.practice.servicedesc.web.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not founded."));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
