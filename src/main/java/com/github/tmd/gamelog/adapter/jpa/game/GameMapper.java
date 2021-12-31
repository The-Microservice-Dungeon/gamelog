package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.domain.game.Game;
import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.game.Round;
import com.github.tmd.gamelog.domain.game.Round.RoundId;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {
  Game toDomain(GameJpa gameJpa) {
    var rounds = gameJpa.getRounds().stream().map(this::toDomain).collect(Collectors.toSet());
    var game = new Game(new GameId(gameJpa.getGameId()), rounds);
    game.setGameStatus(gameJpa.getStatus());
    return game;
  }

  GameJpa toPersistence(Game game) {
    var gameJpa = new GameJpa();
    gameJpa.setGameId(game.getId().gameId());
    gameJpa.setStatus(game.getGameStatus());
    gameJpa.setRounds(game.getRounds().stream().map(this::toPersistence).collect(Collectors.toSet()));
    return gameJpa;
  }

  private RoundJpa toPersistence(Round round) {
    var jpa = new RoundJpa();
    jpa.setRoundId(round.getRoundId().roundId());
    jpa.setRoundNumber(round.getRoundNumber());
    return jpa;
  }

  private Round toDomain(RoundJpa roundJpa) {
    return new Round(new RoundId(roundJpa.getRoundId()), roundJpa.getRoundNumber());
  }
}
