package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.Game.GameId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GameRepository {
  Optional<Game> findGameById(GameId gameId);
  Optional<Game> findActiveGame();
  Optional<Game> findLatestGame();
  Set<Game> findAllGames();
}
