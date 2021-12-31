package com.github.tmd.gamelog.domain.scoreboard.repository;

import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import java.util.Optional;

public interface ScoreboardRepository {
  Scoreboard save(Scoreboard scoreboard);
  Optional<Scoreboard> getScoreboardByGameId(GameId gameId);
}
