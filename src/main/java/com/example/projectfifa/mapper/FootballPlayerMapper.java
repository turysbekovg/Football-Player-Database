package com.example.projectfifa.mapper;

import com.example.projectfifa.dto.response.FootballPlayerDtoResponse;
import com.example.projectfifa.module.FootballPlayer;

public class FootballPlayerMapper {

    public static FootballPlayerDtoResponse footballPlayerToDto(FootballPlayer footballPlayer){
        FootballPlayerDtoResponse footballPlayerDtoResponse = new FootballPlayerDtoResponse();

        footballPlayerDtoResponse.setId(footballPlayer.getId());

        footballPlayerDtoResponse.setName(footballPlayer.getName());

        footballPlayerDtoResponse.setSurname(footballPlayer.getSurname());

        footballPlayerDtoResponse.setHeight(footballPlayer.getHeight());

        footballPlayerDtoResponse.setRating(footballPlayer.getRating());

        footballPlayerDtoResponse.setCountry(footballPlayer.getCountry());

        return footballPlayerDtoResponse;
    }
}
