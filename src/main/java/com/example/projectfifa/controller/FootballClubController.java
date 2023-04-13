package com.example.projectfifa.controller;


import com.example.projectfifa.dto.request.FootballClubDtoRequest;
import com.example.projectfifa.dto.response.FootballClubDtoResponse;
import com.example.projectfifa.exception.ExceptionHandling;
import com.example.projectfifa.mapper.FootballClubMapper;
import com.example.projectfifa.module.FootballClub;
import com.example.projectfifa.service.FootballClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ROLE_FOOTBALL_CLUB_ADMIN')")
@RestController
@RequestMapping("/api/v1/football-club")
@RequiredArgsConstructor
public class FootballClubController extends ExceptionHandling {
    private final FootballClubService footballClubService;


    // @PreAuthorize("hasRole('ROLE_FOOTBALL_CLUB_ADMIN_CREATE')")
    @PostMapping("/create")
    public ResponseEntity<FootballClubDtoResponse> create (@RequestBody FootballClubDtoRequest dtoRequest){
        FootballClub footballClub = footballClubService.create(dtoRequest);

        FootballClubDtoResponse footballClubDtoResponse = FootballClubMapper.footballClubToDto(footballClub);

        return new ResponseEntity<>(footballClubDtoResponse, HttpStatus.CREATED);
    }


    // @PreAuthorize("hasRole('ROLE_FOOTBALL_CLUB_ADMIN_UPDATE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<FootballClubDtoResponse> update(@RequestBody FootballClubDtoRequest dtoRequest,
                                                          @PathVariable(name="id") Long id) {
        FootballClub footballClub = footballClubService.update(dtoRequest, id);

        FootballClubDtoResponse footballClubDtoResponse = FootballClubMapper.footballClubToDto(footballClub);

        return new ResponseEntity<>(footballClubDtoResponse, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('ROLE_FOOTBALL_CLUB_ADMIN_DELETE')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name="id") Long id) {
        footballClubService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
