package com.example.projectfifa.service;


import com.example.projectfifa.dto.request.FootballPlayerDtoRequest;
import com.example.projectfifa.module.FootballPlayer;

import java.security.Principal;
import java.util.Optional;

public interface FootballPlayerService {

    Optional<FootballPlayer> getById(Long id);  // to find player by their id, if the player is not found Optional will return null

    FootballPlayer getByIdThrowException(Long id); // method which throws exception when a player is not found (wont return null)

    FootballPlayer create(FootballPlayerDtoRequest dtoRequest, Principal principal);

    FootballPlayer update(FootballPlayerDtoRequest dtoRequest, Long id);

    void delete(Long id); // delete by id
}
