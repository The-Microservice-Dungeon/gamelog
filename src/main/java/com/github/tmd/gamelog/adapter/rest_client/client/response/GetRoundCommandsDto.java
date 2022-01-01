package com.github.tmd.gamelog.adapter.rest_client.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public record GetRoundCommandsDto(
    @JsonProperty("gameId") UUID gameId,
    @JsonProperty("roundId") UUID roundId,
    @JsonProperty("roundNumber") Integer roundNumber,
    @JsonProperty("commands") Set<ExecutedCommand> commands
    ) { }

record ExecutedCommand(
    @JsonProperty("transactionId") UUID transactionId,
    @JsonProperty("gameId") UUID gameId,
    @JsonProperty("playerId") UUID playerId,
    @JsonProperty("robotId") UUID robotId,
    @JsonProperty("commandType") String commandType
) {}
