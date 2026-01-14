package com.neongames.leaderboard.controller;

import com.neongames.leaderboard.entity.Leaderboard;
import com.neongames.leaderboard.repository.LeaderboardRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin // needed later for frontend
public class LeaderboardController {

    private final LeaderboardRepository repo;

    public LeaderboardController(LeaderboardRepository repo) {
        this.repo = repo;
    }

    // Add score
    @PostMapping
public Leaderboard addScore(@RequestBody Leaderboard entry) {

    return repo.findByPlayerNameAndGameName(
            entry.getPlayerName(),
            entry.getGameName()
    ).map(existing -> {
        existing.setScore(existing.getScore() + entry.getScore());
        return repo.save(existing);
    }).orElseGet(() -> repo.save(entry));
}


    // Get leaderboard by game
   @GetMapping("/{gameName}")
    public List<Leaderboard> getLeaderboard(@PathVariable String gameName) {
        return repo.findByGameNameOrderByScoreDesc(
            gameName.toUpperCase(),
            PageRequest.of(0, 10)
        );
    }

}

