package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record FightingEvent(
    @JsonProperty("success") Boolean success,
    @JsonProperty("message") String message,
    @JsonProperty("attacker") UUID attacker,
    @JsonProperty("defender") UUID defender,
    @JsonProperty("remainingDefenderHealth") Integer remainingDefenderHealth,
    @JsonProperty("remainingEnergy") Integer remainingEnergy
) {

}
