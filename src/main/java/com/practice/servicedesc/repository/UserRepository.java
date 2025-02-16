package com.practice.servicedesc.repository;

import com.practice.servicedesc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);
}
