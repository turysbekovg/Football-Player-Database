package com.example.projectfifa.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserAuthorizationDtoRequest { // what user send us when trying to authorize

    @NotBlank(message = "Login needs to be provided.")
    private String username;

    @NotBlank(message = "Password needs to be provided.")
    private String password;
}
