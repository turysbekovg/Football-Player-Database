package com.example.projectfifa.module;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "players_and_clubs", uniqueConstraints = {   // making their bound unique
        @UniqueConstraint(columnNames = {"football_player_id", "football_club_id"})
})
@Getter
@Setter
public class PlayerClub {   // bridge class

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "football_player_id")
    private FootballPlayer footballPlayer;

    @ManyToOne
    @JoinColumn(name = "football_club_id")
    private FootballClub footballClub;

}
