package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ResourceReplenishedEvent(
    @JsonProperty("planet_id") UUID planetId,
    @JsonProperty("resource") UUID resource,
    @JsonProperty("resource_type") ResourceType resourceType
) { }