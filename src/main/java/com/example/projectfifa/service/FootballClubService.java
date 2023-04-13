package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.FootballClubDtoRequest;
import com.example.projectfifa.module.FootballClub;

import java.util.Optional;

public interface FootballClubService {

    Optional<FootballClub> getById(Long id); // // to find a club by id (null if not found)

    FootballClub getByIdThrowException(Long id); // to find by id (exception if not found)

    FootballClub create(FootballClubDtoRequest dtoRequest);

    FootballClub update(FootballClubDtoRequest dtoRequest, Long id);

    void delete(Long id);
}
