package com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScoreboardEntryJsonDto(
    @JsonProperty(value = "player", required = true) ScoreboardPlayerEntryJsonDto player,
    @JsonProperty(value = "score", required = true) Double score
) {

}
