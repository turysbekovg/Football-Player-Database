package com.example.projectfifa.mapper;

import com.example.projectfifa.dto.response.PlayerClubDtoResponse;
import com.example.projectfifa.module.PlayerClub;

public class PlayerClubMapper {

    public static PlayerClubDtoResponse playerClubToDto(PlayerClub playerClub) {
        PlayerClubDtoResponse playerClubDtoResponse = new PlayerClubDtoResponse();

        playerClubDtoResponse.setId(playerClub.getId());

        playerClubDtoResponse.setFootballPlayer(FootballPlayerMapper.footballPlayerToDto(playerClub.getFootballPlayer()));

        playerClubDtoResponse.setFootballClub(FootballClubMapper.footballClubToDto(playerClub.getFootballClub()));

        return playerClubDtoResponse;
    }
}
