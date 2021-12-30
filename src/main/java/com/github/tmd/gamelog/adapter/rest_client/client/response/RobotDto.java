package com.github.tmd.gamelog.adapter.rest_client.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public record RobotDto(
    @JsonProperty("player") String player
) { }
