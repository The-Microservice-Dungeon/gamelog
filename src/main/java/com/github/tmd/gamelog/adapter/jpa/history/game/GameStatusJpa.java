package com.github.tmd.gamelog.adapter.jpa.history.game;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;

public enum GameStatusJpa {
  CREATED,
  STARTED,
  ENDED;

  public static GameStatusJpa fromGameStatus(GameStatus gameStatus) {
    return switch (gameStatus) {
      case STARTED -> STARTED;
      case ENDED -> ENDED;
      case CREATED -> CREATED;
    };
  }
}
