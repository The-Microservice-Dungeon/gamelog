package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoundStatus {
  @JsonProperty("started")
  STARTED,
  @JsonProperty("command input ended")
  COMMAND_INPUT_ENDED,
  @JsonProperty("ended")
  ENDED;
}
