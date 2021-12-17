package com.github.tmd.gamelog.adapter.event.gameEvent.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record SpacestationCreatedEvent(
    @JsonProperty("planet_id") UUID planetId
) {}
