package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.domain.game.Game;
import com.github.tmd.gamelog.domain.game.Game.GameId;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {
  Game toDomain(GameJpa gameJpa) {
    var game = new Game(new GameId(gameJpa.getGameId()));
    game.setGameStatus(gameJpa.getStatus());
    return game;
  }

  GameJpa toPersistence(Game game) {
    var gameJpa = new GameJpa();
    gameJpa.setGameId(game.getId().gameId());
    gameJpa.setStatus(game.getGameStatus());
    return gameJpa;
  }

  GameJpa update(GameJpa gameJpa, Game game) {
    gameJpa.setStatus(game.getGameStatus());
    return gameJpa;
  }
}
