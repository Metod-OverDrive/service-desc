package com.practice.servicedesc.web.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.servicedesc.entity.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private Boolean isActive;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
