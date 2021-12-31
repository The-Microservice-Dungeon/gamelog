package com.github.tmd.gamelog.domain.scoreboard.service;

import com.github.tmd.gamelog.domain.scoreboard.model.Game;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.Round;
import com.github.tmd.gamelog.domain.scoreboard.model.Round.RoundId;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.repository.ScoreboardRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddRoundToScoreboardService {
  private final ScoreboardRepository scoreboardRepository;

  public AddRoundToScoreboardService(
      ScoreboardRepository scoreboardRepository) {
    this.scoreboardRepository = scoreboardRepository;
  }

  public Scoreboard addRoundToScoreboard(UUID gameId, UUID roundId, Integer roundNumber) {
    var scoreboard=  this.scoreboardRepository.getScoreboardByGameId(new GameId(gameId)).orElseThrow();
    scoreboard.getGame().addRound(new Round(new RoundId(roundId), roundNumber));
    return this.scoreboardRepository.save(scoreboard);
  }

}
