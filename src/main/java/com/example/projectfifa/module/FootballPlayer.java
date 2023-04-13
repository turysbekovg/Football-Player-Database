package com.example.projectfifa.module;


import com.example.projectfifa.module.security.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "football_players") // table name
@Getter
@Setter
public class FootballPlayer {

    @Id // creating id of a player
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // to make id increase automatically
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "country", nullable = false)
    private String country;  // which country a player represents

    @ManyToOne
    @JoinColumn(name = "creator_user_id") // who created a player
    private User creator;


}
