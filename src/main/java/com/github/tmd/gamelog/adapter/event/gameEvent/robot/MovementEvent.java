package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.moshi.Json;
import java.util.List;
import java.util.UUID;

public record MovementEvent(
    @JsonProperty("success") Boolean success,
    @JsonProperty("message") String message,
    @JsonProperty("remainingEnergy") Integer remainingEnergy,
    @JsonProperty("robots") List<UUID> robots,
    @JsonProperty("planet") MovementEventPlanet planet
) {

}

record MovementEventPlanet(
    @JsonProperty("planetId") UUID planetId,
    @JsonProperty("movementDifficulty") Integer movementDifficulty,
    @JsonProperty("planetType") String planetType,
    @JsonProperty("resourceType") String resourceType
) {}