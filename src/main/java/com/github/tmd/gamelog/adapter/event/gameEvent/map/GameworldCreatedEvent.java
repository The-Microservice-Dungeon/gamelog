package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public record GameworldCreatedEvent(
    @JsonProperty("id") UUID id,
    @JsonProperty("spacestation_ids") Set<UUID> spaceStationIds,
    @JsonProperty("status") GameworldStatus status
) {}
