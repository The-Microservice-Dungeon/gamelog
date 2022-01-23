package com.github.tmd.gamelog.adapter.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO class for aggregating player actions into statistics.
 */
@Data
@AllArgsConstructor
public class PlayerStatisticsDto {

    private PlayerDto playerDto;
    private int kills;
    private int earnedMoney;
    private int minedResources;
    private int traveledDistance;

    public PlayerStatisticsDto() {
        playerDto = new PlayerDto();
        kills = 0;
        earnedMoney = 0;
        minedResources = 0;
        traveledDistance = 0;
    }
}