package com.neongames.leaderboard.repository;

import org.springframework.data.domain.Pageable;

import com.neongames.leaderboard.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import java.util.Optional;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> 
{
    List<Leaderboard> findByGameNameOrderByScoreDesc(String gameName, Pageable pageable);

    Optional<Leaderboard> findByPlayerNameAndGameName(String playerName, String gameName);
}


