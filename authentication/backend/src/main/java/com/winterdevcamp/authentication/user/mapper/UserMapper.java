package com.winterdevcamp.authentication.user.mapper;

import com.winterdevcamp.authentication.user.UserEntity;
import com.winterdevcamp.authentication.dto.SignUpReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(SignUpReqDto signUpReqDto);
}
