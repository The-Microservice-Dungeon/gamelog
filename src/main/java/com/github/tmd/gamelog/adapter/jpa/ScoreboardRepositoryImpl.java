package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.GameRepository;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.repository.ScoreboardRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardRepositoryImpl implements ScoreboardRepository {
  private final ScoreboardService scoreboardService;
  private final GameRepository gameRepository;

  public ScoreboardRepositoryImpl(
      ScoreboardService scoreboardService,
      GameRepository gameRepository) {
    this.scoreboardService = scoreboardService;
    this.gameRepository = gameRepository;
  }

  @Override
  public Optional<Scoreboard> getScoreboardByGameId(GameId gameId) {
    return this.scoreboardService.getScoreboardByGameId(gameId);
  }

  @Override
  public Optional<Scoreboard> getScoreboardOfActiveGame() {
    return this.gameRepository.findActiveGame()
        .flatMap(game -> this.scoreboardService.getScoreboardByGameId(game.getId()));
  }
}
