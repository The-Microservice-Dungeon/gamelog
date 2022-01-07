package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tmd.gamelog.adapter.event.gameEvent.map.ResourceType;

public record MiningEvent(
    @JsonProperty("success") Boolean success,
    @JsonProperty("message") String message,
    @JsonProperty("remainingEnergy") Integer remainingEnergy,
    @JsonProperty("updatedInventory") Integer updateInventory,
    @JsonProperty("resourceType") ResourceType resourceType
) {

}
