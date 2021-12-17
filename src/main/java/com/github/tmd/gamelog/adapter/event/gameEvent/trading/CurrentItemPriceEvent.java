package com.github.tmd.gamelog.adapter.event.gameEvent.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.moshi.Json;

public record CurrentItemPriceEvent(
    @JsonProperty("name") String name,
    @JsonProperty("price") Integer price
) { }
