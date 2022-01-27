package com.github.tmd.gamelog.adapter.rest.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetGamePlayersDto(
    @JsonProperty("_embedded") GamePlayersWrapperDto gamePlayersWrapper
) {

}
