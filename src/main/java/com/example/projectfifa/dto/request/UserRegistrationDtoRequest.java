package com.example.projectfifa.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserRegistrationDtoRequest {  // what client passes to us when first register

    @NotBlank(message = "Login needs to be provided.")
    private String username;

    @NotBlank(message = "Password needs to be provided.")
    private String password;

    @NotBlank(message = "Name needs to be provided.")
    private String name;
}
