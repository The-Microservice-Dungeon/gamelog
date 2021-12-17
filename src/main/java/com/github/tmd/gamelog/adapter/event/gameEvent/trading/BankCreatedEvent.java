package com.github.tmd.gamelog.adapter.event.gameEvent.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record BankCreatedEvent(
    @JsonProperty("playerId") UUID playerId,
    @JsonProperty("money") Integer money
) { }
