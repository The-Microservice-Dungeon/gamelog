package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record RegenerationEvent(
    @JsonProperty("success") Boolean success,
    @JsonProperty("message") String message,
    @JsonProperty("remainingEnergy") Integer remainingEnergy
) {

}
