package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.history.game.GameStatusHistoryJpa;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {
  public Game toDomain(GameStatusHistoryJpa gameStatusHistoryJpa) {
    return new Game(new GameId(gameStatusHistoryJpa.getGameId()));
  }
}
