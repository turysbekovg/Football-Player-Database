package com.example.projectfifa.controller;


import com.example.projectfifa.dto.request.PlayerClubDtoRequest;
import com.example.projectfifa.dto.response.PlayerClubDtoResponse;
import com.example.projectfifa.exception.ExceptionHandling;
import com.example.projectfifa.mapper.PlayerClubMapper;
import com.example.projectfifa.module.PlayerClub;
import com.example.projectfifa.service.PlayerClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ROLE_PLAYER_CLUB_ADMIN')")
@RestController
@RequestMapping("/api/v1/player-club")
@RequiredArgsConstructor
public class PlayerClubController extends ExceptionHandling {
    private final PlayerClubService playerClubService;

    @PostMapping("/create")
    public ResponseEntity<PlayerClubDtoResponse> create(@RequestBody PlayerClubDtoRequest dtoRequest) {
        PlayerClub playerClub = playerClubService.create(dtoRequest);

        PlayerClubDtoResponse playerClubDtoResponse = PlayerClubMapper.playerClubToDto(playerClub);

        return new ResponseEntity<>(playerClubDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable (name="id") Long id) {
       playerClubService.delete(id);

       return new ResponseEntity<>(HttpStatus.OK);
    }
}
