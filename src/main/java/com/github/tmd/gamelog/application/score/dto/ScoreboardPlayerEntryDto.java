package com.github.tmd.gamelog.application.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ScoreboardPlayerEntryDto(
    @JsonProperty(value = "id", required = true) UUID id,
    @JsonProperty("name") String name
) {

}
