package com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ScoreboardJsonDto(
    @JsonProperty(value = "scoreboard", required = true) List<ScoreboardEntryJsonDto> scoreboard
) {

}
