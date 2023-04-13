package com.example.projectfifa.controller;

import com.example.projectfifa.dto.request.UserAuthorizationDtoRequest;
import com.example.projectfifa.dto.request.UserRegistrationDtoRequest;
import com.example.projectfifa.dto.response.UserDtoResponse;
import com.example.projectfifa.exception.ExceptionHandling;
import com.example.projectfifa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

// we don't make role for this controller, because its methods are available to everyone
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {  // extends is for Show message of exception to client

    private final UserService userService;

    @PostMapping("/registration")
    // @Valid -> to check if not null/not blank
    public ResponseEntity<HttpStatus> registration(@Valid @RequestBody UserRegistrationDtoRequest dtoRequest) {
        userService.registration(dtoRequest); // if error -> exception

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authorization")
    public ResponseEntity<UserDtoResponse> authorization (@Valid @RequestBody UserAuthorizationDtoRequest dtoRequest,
                                                            HttpServletRequest request) {
        return userService.authorization(dtoRequest, request);
    }


}
