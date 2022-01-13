package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record ItemPriceHistoryEntryDto(
    @JsonProperty("name") String name,
    @JsonProperty("history") Map<String, Integer> history
) {

}
