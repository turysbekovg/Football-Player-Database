package com.example.projectfifa.dto.response;

import com.example.projectfifa.module.FootballPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerClubDtoResponse {

    private Long id;

    private FootballPlayerDtoResponse footballPlayer;

    private FootballClubDtoResponse footballClub;
}
