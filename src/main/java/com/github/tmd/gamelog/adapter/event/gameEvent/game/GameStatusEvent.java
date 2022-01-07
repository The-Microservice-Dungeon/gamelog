package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Game Status changed event using a Java Record as an immutable
 * data holder. Not necessary to generate Getters, Setters, ToString, Equals and HashCode.
 * Kinda like data classes in Kotlin.
 */
public record GameStatusEvent(
    @JsonProperty("gameId") UUID gameId,
    @JsonProperty("status") GameStatus status
) {}

  /*
  @AllArgsConstructor
  @NoArgsConstructor
public class GameStatusEvent {
  @JsonProperty("gameId")
  private UUID gameId;
  @JsonProperty("status")
  private GameStatus status;

  public GameStatus status() {
    return status;
  }

  public UUID gameId() {
    return gameId;
  }
}
*/