package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Game Status changed event using a Java Record as an immutable
 * data holder. Not necessary to generate Getters, Setters, ToString, Equals and HashCode.
 * Kinda like data classes in Kotlin.
 */
public record GameStatusEvent(
    @JsonProperty("gameId") UUID gameId,
    @JsonProperty("gameStatus") GameStatus status
) {
}
