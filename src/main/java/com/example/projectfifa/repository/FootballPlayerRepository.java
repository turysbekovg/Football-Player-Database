package com.example.projectfifa.repository;

import com.example.projectfifa.module.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, Long> {  // repository for FootbPlayer
}
