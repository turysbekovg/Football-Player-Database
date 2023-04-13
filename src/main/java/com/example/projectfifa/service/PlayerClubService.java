package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.PlayerClubDtoRequest;
import com.example.projectfifa.module.PlayerClub;

import java.util.Optional;

public interface PlayerClubService {

    Optional<PlayerClub> getById(Long id);

    PlayerClub getByIdThrowException(Long id);

    PlayerClub create(PlayerClubDtoRequest playerClubDtoRequest);

    void delete(Long id);

}
