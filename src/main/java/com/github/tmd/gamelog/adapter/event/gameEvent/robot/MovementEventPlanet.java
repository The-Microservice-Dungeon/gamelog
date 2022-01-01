package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record MovementEventPlanet(
    @JsonProperty("planetId") UUID planetId,
    @JsonProperty("movementDifficulty") Integer movementDifficulty,
    @JsonProperty("planetType") String planetType,
    @JsonProperty("resourceType") String resourceType
) {

}
