package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.UUID;

public record ExecutedCommand(
    @JsonProperty("transactionId") UUID transactionId,
    @JsonProperty("gameId") UUID gameId,
    @JsonProperty("playerId") UUID playerId,
    @JsonProperty("robotId") UUID robotId,
    @JsonProperty("commandType") String commandType,
    @JsonProperty("commandObject") Map<String, String> commandObject
    ) {

}
