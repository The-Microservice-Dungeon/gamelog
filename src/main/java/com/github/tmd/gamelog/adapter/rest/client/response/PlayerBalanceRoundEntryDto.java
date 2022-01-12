package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PlayerBalanceRoundEntryDto(
    @JsonProperty("round") Integer roundNumber,
    @JsonProperty("playerId") UUID playerId,
    @JsonProperty("balance") Integer balance) { }
