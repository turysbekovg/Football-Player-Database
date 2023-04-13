package com.example.projectfifa.repository;

import com.example.projectfifa.module.PlayerClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerClubRepository extends JpaRepository<PlayerClub, Long> {  // repository for PlayerClub
}
