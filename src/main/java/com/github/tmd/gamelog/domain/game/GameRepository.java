package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import java.util.Optional;

public interface GameRepository {
  Game save(Game game);
  Optional<Game> findGameById(GameId gameId);
}
