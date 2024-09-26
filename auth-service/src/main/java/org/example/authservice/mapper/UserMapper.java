package org.example.authservice.mapper;

import org.example.authservice.dto.RegisterRequest;
import org.example.authservice.entity.UserCredential;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCredential toUserEntity(RegisterRequest registerRequest);

    RegisterRequest toRegisterRequest(UserCredential userCredential);
}