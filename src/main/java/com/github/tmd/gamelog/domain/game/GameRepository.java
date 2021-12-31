package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.game.Round.RoundId;
import java.util.Optional;

public interface GameRepository {
  Game save(Game game);
  Optional<Game> findGameById(GameId gameId);
  Optional<Game> findGameByRoundId(RoundId roundId);
}
