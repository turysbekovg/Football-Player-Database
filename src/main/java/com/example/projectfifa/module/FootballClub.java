package com.example.projectfifa.module;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "football_clubs")
@Getter
@Setter
public class FootballClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)  // name of a club
    private String title;

    @Column(name = "club_country")
    private String clubCountry; // to which country a club is related to
}
