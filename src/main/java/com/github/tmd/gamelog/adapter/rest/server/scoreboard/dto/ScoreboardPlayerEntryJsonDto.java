package com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ScoreboardPlayerEntryJsonDto(
    @JsonProperty(value = "id", required = true) UUID id,
    @JsonProperty("name") String name
) {

}
