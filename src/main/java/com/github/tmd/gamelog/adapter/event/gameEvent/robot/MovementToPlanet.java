package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.PlanetType;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceType;
import java.util.UUID;

public record MovementToPlanet(
    @JsonProperty("planetId") UUID planetId,
    @JsonProperty("movementDifficulty") Integer movementDifficulty,
    @JsonProperty("planetType") PlanetType planetType,
    @JsonProperty("resourceType") ResourceType resourceType
) {
}
