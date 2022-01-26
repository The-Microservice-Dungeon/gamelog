package com.github.tmd.gamelog.adapter.view.model;

import lombok.Builder;

public record Placement(Double totalScore,
                        Double fightingScore,
                        Double miningScore,
                        Double movementScore,
                        Double robotScore,
                        Double tradingScore,
                        String playerName, String playerId) {

}
