package com.example.projectfifa.controller;

import com.example.projectfifa.dto.request.FootballPlayerDtoRequest;
import com.example.projectfifa.dto.response.FootballPlayerDtoResponse;
import com.example.projectfifa.exception.ExceptionHandling;
import com.example.projectfifa.mapper.FootballPlayerMapper;
import com.example.projectfifa.module.FootballPlayer;
import com.example.projectfifa.service.FootballPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


//@PreAuthorize("hasRole('ROLE_FOOTBALL_PLAYER_ADMIN')")
@RestController
@RequestMapping("/api/v1/football-player")
@RequiredArgsConstructor
public class FootballPlayerController extends ExceptionHandling {

    private final FootballPlayerService footballPlayerService;

    @PreAuthorize("@customPreAuthorizeService.validateRequest('/football-player/create')") // every authority its bound to a method
    @PostMapping("/create")
    public ResponseEntity<FootballPlayerDtoResponse> create(@RequestBody FootballPlayerDtoRequest dtoRequest,
                                                            Principal principal){  // principal -> info about person who enter to system and makes request
        FootballPlayer footballPlayer = footballPlayerService.create(dtoRequest, principal);

        FootballPlayerDtoResponse footballPlayerDtoResponse = FootballPlayerMapper.footballPlayerToDto(footballPlayer);

        return new ResponseEntity<>(footballPlayerDtoResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('/football-player/update/')")
    @PutMapping("/update/{id}")
    public ResponseEntity<FootballPlayerDtoResponse> update(@RequestBody FootballPlayerDtoRequest dtoRequest,
                                                            @PathVariable(name="id") Long id) {
        FootballPlayer footballPlayer = footballPlayerService.update(dtoRequest, id);

        FootballPlayerDtoResponse footballPlayerDtoResponse = FootballPlayerMapper.footballPlayerToDto(footballPlayer);

        return new ResponseEntity<>(footballPlayerDtoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/football-player/delete/')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name="id") Long id){
        footballPlayerService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
