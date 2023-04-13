package com.example.projectfifa.mapper;

import com.example.projectfifa.dto.response.UserDtoResponse;
import com.example.projectfifa.module.security.User;

public class UserMapper { // class to change User to UserDtoResponse
    public static UserDtoResponse userToDto(User user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();

        userDtoResponse.setUsername(user.getUsername());
        userDtoResponse.setName(user.getName());

        return userDtoResponse;
    }
}
