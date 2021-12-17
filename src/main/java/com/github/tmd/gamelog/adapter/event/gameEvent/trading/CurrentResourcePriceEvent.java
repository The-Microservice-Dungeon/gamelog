package com.github.tmd.gamelog.adapter.event.gameEvent.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentResourcePriceEvent(
    @JsonProperty("name") String name,
    @JsonProperty("price") Integer price
) { }
