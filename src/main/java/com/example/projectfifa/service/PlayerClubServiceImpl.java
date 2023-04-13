package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.PlayerClubDtoRequest;
import com.example.projectfifa.exception.CustomExceptionMessage;
import com.example.projectfifa.exception.custom.NotFoundException;
import com.example.projectfifa.exception.custom.RepositoryCreateException;
import com.example.projectfifa.exception.custom.RepositoryDeleteException;
import com.example.projectfifa.module.FootballClub;
import com.example.projectfifa.module.FootballPlayer;
import com.example.projectfifa.module.PlayerClub;
import com.example.projectfifa.repository.PlayerClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerClubServiceImpl implements PlayerClubService {

    private final PlayerClubRepository playerClubRepository;

    private final FootballPlayerService footballPlayerService; // we need to pass the whole objects
    private final FootballClubService footballClubService;

    private PlayerClub save(PlayerClub playerClub){
        return playerClubRepository.save(playerClub);  // saving playerClub bound
    }

    @Override
    public Optional<PlayerClub> getById(Long id) {
        return playerClubRepository.findById(id);
    }

    @Override
    public PlayerClub getByIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public PlayerClub create(PlayerClubDtoRequest playerClubDtoRequest) {

        PlayerClub playerClub = new PlayerClub(); // we need to pass the whole objects

        try {
            FootballPlayer footballPlayer = footballPlayerService.getByIdThrowException(playerClubDtoRequest.getFootballPlayerId());

            FootballClub footballClub = footballClubService.getByIdThrowException(playerClubDtoRequest.getFootballClubId());

            playerClub.setFootballPlayer(footballPlayer); // setting Player
            playerClub.setFootballClub(footballClub); // setting Club

            return this.save(playerClub);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void delete(Long id) {

        PlayerClub playerClub = getByIdThrowException(id);

        try {
            playerClubRepository.delete(playerClub);
        } catch (Exception e){
            e.printStackTrace();
            throw new RepositoryDeleteException(CustomExceptionMessage.DELETE_EXCEPTION_MESSAGE);
        }

    }
}
