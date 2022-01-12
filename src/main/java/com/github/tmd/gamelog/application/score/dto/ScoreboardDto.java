package com.github.tmd.gamelog.application.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public record ScoreboardDto(
    @JsonProperty(value = "scoreboard", required = true) List<ScoreboardEntryDto> scoreboard
) {

}
