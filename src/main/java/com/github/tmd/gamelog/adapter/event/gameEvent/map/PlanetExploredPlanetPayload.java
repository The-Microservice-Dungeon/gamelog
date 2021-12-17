package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public record PlanetExploredPlanetPayload(
    @JsonProperty("resource_id") UUID resourceId,
    @JsonProperty("neighbour_ids") Set<UUID> neighbourIds,
    @JsonProperty("recharge_multiplicator") Integer rechargeMultiplicator,
    @JsonProperty("movement_difficulty") Integer movementDifficulty,
    @JsonProperty("planet_type") PlanetType planetType
) {}