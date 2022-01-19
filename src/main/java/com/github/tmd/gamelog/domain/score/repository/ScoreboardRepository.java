package com.github.tmd.gamelog.domain.score.repository;

import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import java.util.Optional;

public interface ScoreboardRepository {
  Optional<Scoreboard> getScoreboardByGameId(GameId gameId);
  Optional<Scoreboard> getScoreboardOfActiveGame();
}
