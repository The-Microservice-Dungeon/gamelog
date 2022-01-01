package com.github.tmd.gamelog.domain.scoreboard.service;

import com.github.tmd.gamelog.domain.scoreboard.model.Game;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard.ScoreboardId;
import com.github.tmd.gamelog.domain.scoreboard.repository.ScoreboardRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class LockScoreboardService {
  private final ScoreboardRepository scoreboardRepository;

  public LockScoreboardService(
      ScoreboardRepository scoreboardRepository) {
    this.scoreboardRepository = scoreboardRepository;
  }

  public Scoreboard lockScoreboard(UUID gameId) {
    var game = new Game(new GameId(gameId), new HashSet<>());
    var scoreboard = new Scoreboard(new ScoreboardId(UUID.randomUUID()), game, new HashMap<>());

    scoreboard.lock();

    return this.scoreboardRepository.save(scoreboard);
  }
}
