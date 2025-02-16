package com.practice.servicedesc.web.mapper;

import com.practice.servicedesc.entity.User;
import com.practice.servicedesc.web.dto.user.UserSpecDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSpecMapper extends Mappable<User, UserSpecDto>{

    User toEntity(UserSpecDto dto);

    UserSpecDto toDto(User entity);
}
