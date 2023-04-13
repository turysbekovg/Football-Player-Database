package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.FootballPlayerDtoRequest;
import com.example.projectfifa.exception.CustomExceptionMessage;
import com.example.projectfifa.exception.custom.NotFoundException;
import com.example.projectfifa.exception.custom.RepositoryCreateException;
import com.example.projectfifa.exception.custom.RepositoryDeleteException;
import com.example.projectfifa.exception.custom.RepositoryUpdateException;
import com.example.projectfifa.module.FootballPlayer;
import com.example.projectfifa.repository.FootballPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor  // constructor that adds dependencies
public class FootballPlayerServiceImpl implements FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository; // then with ReqArgsConst it adds constructor

    private final UserService userService;

    private FootballPlayer save(FootballPlayer footballPlayer) {  // creating saving method to later use it in this class
        return footballPlayerRepository.save(footballPlayer);
    }

    @Override
    public Optional<FootballPlayer> getById(Long id) {
        return footballPlayerRepository.findById(id);
        // there is no such method in footballPlayerRepository, but this method is common for all Repositories (by default)
    }

    @Override
    public FootballPlayer getByIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
        // orElseThrow -> throws an exception; NOT_FOUND_MESSAGE is our custom message
    }

    @Override
    public FootballPlayer create(FootballPlayerDtoRequest dtoRequest, Principal principal) {
        FootballPlayer footballPlayer = new FootballPlayer();

        try {   // if error occurs, we will catch it and throw an exception
            footballPlayer.setName(dtoRequest.getName());

            footballPlayer.setSurname(dtoRequest.getSurname());

            footballPlayer.setHeight(dtoRequest.getHeight());

            footballPlayer.setRating(dtoRequest.getRating());

            footballPlayer.setCountry(dtoRequest.getCountry());

            String username = principal.getName();

          //  SecurityContextHolder.getContext().getAuthentication().getName();

            userService.getByUsername(username).ifPresent(footballPlayer::setCreator); // if user present so we set to player

            return this.save(footballPlayer);   // save player

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public FootballPlayer update(FootballPlayerDtoRequest dtoRequest, Long id) {

        FootballPlayer footballPlayer = this.getByIdThrowException(id); // throws an exception if the player w/ id is not found

        try {   // if error occurs, we will catch it and throw an exception

            footballPlayer.setName(dtoRequest.getName());

            footballPlayer.setSurname(dtoRequest.getSurname());

            footballPlayer.setHeight(dtoRequest.getHeight());

            footballPlayer.setRating(dtoRequest.getRating());

            footballPlayer.setCountry(dtoRequest.getCountry());

            return this.save(footballPlayer);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryUpdateException(CustomExceptionMessage.UPDATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void delete(Long id) {

        FootballPlayer footballPlayer = this.getByIdThrowException(id);

        try { // if error -> throw except
            footballPlayerRepository.delete(footballPlayer); // method is default for all Repositories
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryDeleteException(CustomExceptionMessage.DELETE_EXCEPTION_MESSAGE);
        }

    }

}
