package com.github.tmd.gamelog.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandContext {
    private String gameId;
    private int round;
    private String playerId;
}
