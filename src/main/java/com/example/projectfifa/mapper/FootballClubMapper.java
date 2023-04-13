package com.example.projectfifa.mapper;

import com.example.projectfifa.dto.response.FootballClubDtoResponse;
import com.example.projectfifa.module.FootballClub;

public class FootballClubMapper {
    public static FootballClubDtoResponse footballClubToDto(FootballClub footballClub) {
        FootballClubDtoResponse footballClubDtoResponse = new FootballClubDtoResponse();

        footballClubDtoResponse.setId(footballClub.getId());

        footballClubDtoResponse.setTitle(footballClub.getTitle());

        footballClubDtoResponse.setClubCountry(footballClub.getClubCountry());

        return footballClubDtoResponse;
    }
}
