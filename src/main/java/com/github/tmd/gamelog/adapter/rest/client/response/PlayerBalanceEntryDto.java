package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PlayerBalanceEntryDto(
    @JsonProperty("player-id") UUID playerId,
    @JsonProperty("balance") Integer balance) { }
