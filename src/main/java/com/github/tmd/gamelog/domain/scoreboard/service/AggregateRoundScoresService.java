package com.github.tmd.gamelog.domain.scoreboard.service;

import com.github.tmd.gamelog.domain.player.Player;
import com.github.tmd.gamelog.domain.player.PlayerService;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.PlayerGameScore;
import com.github.tmd.gamelog.domain.scoreboard.model.Round;
import com.github.tmd.gamelog.domain.scoreboard.model.RoundScore;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.repository.ScoreboardRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AggregateRoundScoresService {
  private final ScoreboardRepository scoreboardRepository;

  public AggregateRoundScoresService(
      ScoreboardRepository scoreboardRepository) {
    this.scoreboardRepository = scoreboardRepository;
  }

  public Scoreboard aggregate(UUID gameId, UUID roundId) {
    var scoreboard = scoreboardRepository.getScoreboardByGameId(new GameId(gameId))
        .orElseThrow(() -> new RuntimeException("Scoreboard for game with id %s not found".formatted(gameId)));
    var round = scoreboard.getGame().getRounds().stream().filter(r -> r.getRoundId().roundId().equals(roundId)).findFirst().orElseThrow(() -> new RuntimeException("Round with id %s not registered".formatted(roundId)));

    var aggregatedPlayerRoundScores = this.aggregatePlayerRoundScores(round);
    for (var entry : aggregatedPlayerRoundScores.entrySet()) {
      scoreboard.getRoundScores().get(entry.getKey()).addRoundScore(round, entry.getValue());
    }

    var savedScoreboard = scoreboardRepository.save(scoreboard);
    return savedScoreboard;
  }

  private Map<Player, RoundScore> aggregatePlayerRoundScores(Round round) {
    // Here the magic happens.
    // Call external APIs, aggregate scores and set them in the scoreboard via
    // scoreboard.addRoundScore(). All must happen within interfaces and the implementations
    // are found in the infrastructure layer using adapters...

    return Map.of();
  }
}
