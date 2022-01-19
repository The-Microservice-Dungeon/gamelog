package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.repository.ScoreboardRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardRepositoryImpl implements ScoreboardRepository {
  private final ScoreboardService scoreboardService;

  public ScoreboardRepositoryImpl(
      ScoreboardService scoreboardService) {
    this.scoreboardService = scoreboardService;
  }

  @Override
  public Optional<Scoreboard> getScoreboardByGameId(GameId gameId) {
    return this.scoreboardService.getScoreboardByGameId(gameId);
  }

  @Override
  public Optional<Scoreboard> getScoreboardOfActiveGame() {
    return this.scoreboardService.getScoreboardOfActiveGame();
  }
}
