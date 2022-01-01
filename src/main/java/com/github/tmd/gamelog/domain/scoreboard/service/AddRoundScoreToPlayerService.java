package com.github.tmd.gamelog.domain.scoreboard.service;

import com.github.tmd.gamelog.domain.player.PlayerService;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.RoundScore;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.repository.ScoreboardRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddRoundScoreToPlayerService {
  private final ScoreboardRepository scoreboardRepository;
  private final PlayerService playerService;

  public AddRoundScoreToPlayerService(
      ScoreboardRepository scoreboardRepository,
      PlayerService playerService) {
    this.scoreboardRepository = scoreboardRepository;
    this.playerService = playerService;
  }

  public Scoreboard addRoundScore(
      UUID gameId,
      UUID roundId,
      UUID playerId
  ) {
    var scoreboard=  this.scoreboardRepository.getScoreboardByGameId(new GameId(gameId)).orElseThrow();
    var player = playerService.getPlayerById(playerId);
    scoreboard.addRoundScore(player, roundId);
    return this.scoreboardRepository.save(scoreboard);
  }
}
