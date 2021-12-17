package com.github.tmd.gamelog.adapter.rest_client.client.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandContextDto {
    String transactionId;
    String gameId;
    String playerId;
    String roundId;
    int roundNumber;
}
