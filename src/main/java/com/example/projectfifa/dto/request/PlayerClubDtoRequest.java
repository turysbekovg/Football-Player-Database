package com.example.projectfifa.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PlayerClubDtoRequest {  // this is DtoRequest for bridge class (for bound)

    @NotNull(message = "The player is needed to be provided.")
    private Long footballPlayerId;  // for bound client will pass id's of player and club

    @NotNull(message = "The club is needed to be provided.")
    private Long footballClubId;
}
