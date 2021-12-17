package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public record FightingItemUseEvent(
    @JsonProperty("success") Boolean success,
    @JsonProperty("message") String message,
    @JsonProperty("associatesFights") Set<UUID> associatesFights,
    @JsonProperty("remainingItemCount") Integer remainingItemCount
) {

}
