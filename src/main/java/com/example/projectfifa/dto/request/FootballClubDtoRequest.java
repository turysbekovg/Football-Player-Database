package com.example.projectfifa.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class FootballClubDtoRequest {

    @NotBlank (message = "Title needs to be provided.") // we cant provide null, or empty string for the parameter 'title' (не пустой)
    private String title;

    @NotBlank(message = "The club's country needs to be provided.")
    private String clubCountry; // to which country a club is related to
}
