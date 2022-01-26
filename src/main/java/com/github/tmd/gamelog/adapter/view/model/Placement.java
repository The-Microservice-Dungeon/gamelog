package com.github.tmd.gamelog.adapter.view.model;

import lombok.Builder;

public record Placement(Double totalScore,
                        Double fightingScore,
                        Double miningScore,
                        Double movementScore,
                        Double robotScore,
                        Double tradingScore,

                        Integer totalPlacement,
                        Integer fightingPlacement,
                        Integer miningPlacement,
                        Integer movementPlacement,
                        Integer tradingPlacement,

                        String playerName, String playerId) {

}
