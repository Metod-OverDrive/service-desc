package com.practice.servicedesc.web.mapper;

import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

}
