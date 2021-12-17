package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Game Status changed event using a Java Record as an immutable
 * data holder. Not necessary to generate Getters, Setters, ToString, Equals and HashCode.
 * Kinda like data classes in Kotlin.
 */
public record GameStatusEvent(
    @JsonProperty("status") GameStatus status
) {
}
