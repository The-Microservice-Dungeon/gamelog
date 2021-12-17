package com.github.tmd.gamelog.adapter.event.gameEvent.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TradingEvent(
    @JsonProperty("success") Boolean success,
    @JsonProperty("moneyChangedBy") Integer amount,
    @JsonProperty("message") String message,
    @JsonProperty("data") Object data
) { }
