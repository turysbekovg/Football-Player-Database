package com.example.projectfifa.dto.request;

import lombok.Getter;
import org.hibernate.annotations.NotFound;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class FootballPlayerDtoRequest {  // to create and update

    @NotBlank(message = "The player's name needs to be provided.")
    private String name;

    private String surname;

    @NotNull(message = "Height needs to be provided.")  // client cant send this parameter as 'null', they obliged to send this parameter
    private Double height;

    @NotNull(message = "Rating needs to be provided.")
    private Double rating;

    @NotBlank(message = "The player's country needs to be provided.")
    private String country;  // which country a player represents
}
