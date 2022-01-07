package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LobbyAction {
  @JsonProperty("joined")
  JOINED,
  @JsonProperty("left")
  LEFT;
}
