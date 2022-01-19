package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PlayerStatusChangedEvent(
    @JsonProperty("playerId") UUID userId,
    @JsonProperty("userName") String userName
) {}
