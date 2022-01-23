package com.github.tmd.gamelog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for aggregating player actions into statistics.
 */
@Data
@AllArgsConstructor
public class PlayerStatistics {

    private Player player;
    private int kills;
    private int earnedMoney;
    private int minedResources;
    private int traveledDistance;

    public PlayerStatistics() {
        player = new Player();
        kills = 0;
        earnedMoney = 0;
        minedResources = 0;
        traveledDistance = 0;
    }
}
