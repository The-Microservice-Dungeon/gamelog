package com.github.tmd.gamelog.adapter.event.gameEvent.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record RobotRepair(
    @JsonProperty("robotId") UUID robotId
) {

}
