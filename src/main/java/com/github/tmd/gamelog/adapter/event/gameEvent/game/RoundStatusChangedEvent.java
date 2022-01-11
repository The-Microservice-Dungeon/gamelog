package com.github.tmd.gamelog.adapter.event.gameEvent.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record RoundStatusChangedEvent(
    @JsonProperty("roundId") UUID roundId,
    @JsonProperty("roundNumber") Integer roundNumber,
    @JsonProperty("roundStatus") RoundStatus roundStatus
) {}

