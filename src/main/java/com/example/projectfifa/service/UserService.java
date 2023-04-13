package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.UserAuthorizationDtoRequest;
import com.example.projectfifa.dto.request.UserRegistrationDtoRequest;
import com.example.projectfifa.dto.response.UserDtoResponse;
import com.example.projectfifa.module.security.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username); // searches for username

    User getByUsernameThrowException(String username); // searches, but throws an exception

    void registration(UserRegistrationDtoRequest dtoRequest); // this method won't return anything, it just saves user's data in database

    // method returns response (dtoResponse)
    // ResponseEntity because we want so this method returns body (UserDtoResponse) and header (which contains Token), and status as well
    ResponseEntity<UserDtoResponse> authorization(UserAuthorizationDtoRequest dtoRequest, HttpServletRequest request);
}
