package com.github.tmd.gamelog.adapter.jpa.history.game;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.LobbyAction;

public enum GamePlayerStatusJpa {
  JOINED,
  LEFT;

  public static GamePlayerStatusJpa fromLobbyAction(LobbyAction lobbyAction) {
    return switch (lobbyAction) {
      case JOINED -> JOINED;
      case LEFT -> LEFT;
    };
  }
}
