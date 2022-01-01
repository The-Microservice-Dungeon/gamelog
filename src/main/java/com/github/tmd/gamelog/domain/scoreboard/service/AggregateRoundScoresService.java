package com.github.tmd.gamelog.domain.scoreboard.service;

import com.github.tmd.gamelog.domain.player.PlayerService;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.repository.ScoreboardRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AggregateRoundScoresService {
  private final ScoreboardRepository scoreboardRepository;
  private final PlayerService playerService;

  public AggregateRoundScoresService(
      ScoreboardRepository scoreboardRepository,
      PlayerService playerService) {
    this.scoreboardRepository = scoreboardRepository;
    this.playerService = playerService;
  }

  public Scoreboard aggregate(UUID gameId, UUID roundId) {
    var scoreboard = scoreboardRepository.getScoreboardByGameId(new GameId(gameId))
        .orElseThrow(() -> new RuntimeException("Scoreboard for game with id %s not found".formatted(gameId)));

    // Here the magic happens.
    // Call external APIs, aggregate scores and set them in the scoreboard via
    // scoreboard.addRoundScore(). All must happen within interfaces and the implementations
    // are found in the infrastructure layer using adapters...

    return scoreboard;
  }
}
