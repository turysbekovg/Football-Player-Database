package com.example.projectfifa.service;

import com.example.projectfifa.dto.request.FootballClubDtoRequest;
import com.example.projectfifa.exception.CustomExceptionMessage;
import com.example.projectfifa.exception.custom.NotFoundException;
import com.example.projectfifa.exception.custom.RepositoryCreateException;
import com.example.projectfifa.exception.custom.RepositoryDeleteException;
import com.example.projectfifa.exception.custom.RepositoryUpdateException;
import com.example.projectfifa.module.FootballClub;
import com.example.projectfifa.repository.FootballClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballClubServiceImpl implements FootballClubService {

    private final FootballClubRepository footballClubRepository;


    private FootballClub save (FootballClub footballClub){
        return footballClubRepository.save(footballClub);
    }

    @Override
    public Optional<FootballClub> getById(Long id) {
        return footballClubRepository.findById(id);
    }

    @Override
    public FootballClub getByIdThrowException(Long id) {
        return this.getById(id).orElseThrow(() -> new NotFoundException(CustomExceptionMessage.NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public FootballClub create(FootballClubDtoRequest dtoRequest) {
        FootballClub footballClub = new FootballClub();

        try {
            footballClub.setTitle(dtoRequest.getTitle());

            footballClub.setClubCountry(dtoRequest.getClubCountry());

            return this.save(footballClub);
        } catch (Exception e) {
            e.printStackTrace();  // this is to print the exception to console
            throw new RepositoryCreateException(CustomExceptionMessage.CREATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public FootballClub update(FootballClubDtoRequest dtoRequest, Long id) {
        FootballClub footballClub = this.getByIdThrowException(id);

        try {
            footballClub.setTitle(dtoRequest.getTitle());

            footballClub.setClubCountry(dtoRequest.getClubCountry());

            return this.save(footballClub);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryUpdateException(CustomExceptionMessage.UPDATE_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void delete(Long id) {

        FootballClub footballClub = getByIdThrowException(id);

        try {
            footballClubRepository.delete(footballClub);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryDeleteException(CustomExceptionMessage.DELETE_EXCEPTION_MESSAGE);
        }

    }
}
