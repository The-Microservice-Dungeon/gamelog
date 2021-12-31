package com.github.tmd.gamelog.domain.scoreboard;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import java.util.Optional;

public interface ScoreboardRepository {
  Optional<Scoreboard> getScoreboardByGameId(GameId gameId);
}
