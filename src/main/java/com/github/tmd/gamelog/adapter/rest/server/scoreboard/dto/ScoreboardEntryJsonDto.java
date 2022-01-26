package com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScoreboardEntryJsonDto(
    @JsonProperty(value = "player", required = true) ScoreboardPlayerEntryJsonDto player,
    @JsonProperty(value = "totalScore", required = true) Double totalScore,
    @JsonProperty(value = "fightingScore", required = true) Double fightingScore,
    @JsonProperty(value = "miningScore", required = true) Double miningScore,
    @JsonProperty(value = "movementScore", required = true) Double movementScore,
    @JsonProperty(value = "robotScore", required = true) Double robotScore,
    @JsonProperty(value = "tradingScore", required = true) Double tradingScore
) {

}
