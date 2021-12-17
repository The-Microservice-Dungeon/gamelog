package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ResourceDistributionEvent(
    @JsonProperty("robotId") UUID robotId,
    @JsonProperty("coal") Integer coal,
    @JsonProperty("iron") Integer iron,
    @JsonProperty("gem") Integer gem,
    @JsonProperty("gold") Integer gold,
    @JsonProperty("platin") Integer platin
) {

}
