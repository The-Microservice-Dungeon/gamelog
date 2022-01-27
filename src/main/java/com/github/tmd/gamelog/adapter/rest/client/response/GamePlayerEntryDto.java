package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record GamePlayerEntryDto(
    @JsonProperty("playerToken") UUID playerToken,
    @JsonProperty("userName") String userName,
    @JsonProperty("mailAddress") String mailAddress,
    @JsonProperty("_links") GamePlayerLinksDto links
    ) {

}
