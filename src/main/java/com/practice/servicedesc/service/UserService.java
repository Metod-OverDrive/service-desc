package com.practice.servicedesc.service;

import com.practice.servicedesc.entity.User;

public interface UserService {

    User findById(Long id);
    User getByEmail(String email);
}
