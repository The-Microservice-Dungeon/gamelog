package com.github.tmd.gamelog.adapter.jpa.history.game;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.RoundStatus;

public enum GameRoundStatusJpa {
  STARTED,
  COMMAND_INPUT_ENDED,
  ENDED;

  public static GameRoundStatusJpa fromRoundStatus(RoundStatus status) {
    return switch (status) {
      case ENDED -> ENDED;
      case COMMAND_INPUT_ENDED -> COMMAND_INPUT_ENDED;
      case STARTED -> STARTED;
    };
  }
}
