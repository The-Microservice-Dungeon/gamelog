package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GamePlayersWrapperDto(
    @JsonProperty("players") List<GamePlayerEntryDto> players
    ) {

}
