package com.github.tmd.gamelog.application.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScoreboardEntryDto(
    @JsonProperty(value = "player", required = true) ScoreboardPlayerEntryDto player,
    @JsonProperty(value = "score", required = true) Double score
) {

}
