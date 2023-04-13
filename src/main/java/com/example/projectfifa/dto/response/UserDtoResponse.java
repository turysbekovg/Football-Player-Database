package com.example.projectfifa.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoResponse { // what we will return to a client
    private String username; // we will send username and name

    private String name;
}
