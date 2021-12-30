package com.github.tmd.gamelog.adapter.rest_client.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record CommandContextDto(
    @JsonProperty("transactionId") String transactionId,
    @JsonProperty("gameId") String gameId,
    @JsonProperty("playerId") String playerId,
    @JsonProperty("roundId") String roundId,
    @JsonProperty("roundNumber") int roundNumber
) {

}
