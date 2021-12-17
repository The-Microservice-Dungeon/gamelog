package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record RobotDestroyedEvent(
    @JsonProperty("robotId") UUID robotId,
    @JsonProperty("playerId") UUID playerId
) { }
