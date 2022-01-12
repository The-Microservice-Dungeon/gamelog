package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

public record GetRoundCommandsDto(
    @JsonProperty("gameId") UUID gameId,
    @JsonProperty("roundId") UUID roundId,
    @JsonProperty("roundNumber") Integer roundNumber,
    @JsonProperty("commands") Set<ExecutedCommand> commands
    ) { }

