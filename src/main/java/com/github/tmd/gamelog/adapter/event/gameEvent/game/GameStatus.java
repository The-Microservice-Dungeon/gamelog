package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum Constants regarding the possible Game Status values.
 * Since the status can be in lowercase or mixed-case (for whatever reason)
 * it is necessary to set {@code spring.jackson.mapper.accept-case-insensitive-enums=true} in the
 * spring config.
 */
public enum GameStatus {
  @JsonProperty("created")
  CREATED,
  @JsonProperty("started")
  STARTED,
  @JsonProperty("ended")
  ENDED;
}
