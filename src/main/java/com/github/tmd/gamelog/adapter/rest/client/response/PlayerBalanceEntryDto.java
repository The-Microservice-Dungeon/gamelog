package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PlayerBalanceEntryDto(
    @JsonAlias({ "player-id "}) @JsonProperty("playerId") UUID playerId,
    @JsonProperty("balance") Integer balance) { }
