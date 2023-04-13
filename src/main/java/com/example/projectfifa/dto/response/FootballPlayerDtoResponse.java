package com.example.projectfifa.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FootballPlayerDtoResponse {

    private Long id;

    private String name;

    private String surname;

    private Double height;

    private Double rating;

    private String country;
}
