package com.example.projectfifa.repository;

import com.example.projectfifa.module.FootballClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballClubRepository extends JpaRepository<FootballClub, Long> {   // repository for FootbClub
}
