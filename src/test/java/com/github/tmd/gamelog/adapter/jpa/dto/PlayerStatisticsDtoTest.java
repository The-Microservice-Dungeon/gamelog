package com.github.tmd.gamelog.adapter.jpa.dto;

import com.github.tmd.gamelog.domain.trophies.TrophyType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerStatisticsDto class.
 */
public class PlayerStatisticsDtoTest {

    private final UUID playerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");
    private final String playerName = "Max Mustermann";

    @Test
    void testEmptyNewPlayerStatisticsDto() {
        PlayerStatisticsDto playerStatisticsDto = new PlayerStatisticsDto();
        assertThat(playerStatisticsDto.getPlayerDto().getId()).isNull();
        assertThat(playerStatisticsDto.getPlayerDto().getEarnedTrophies()).isEmpty();
        assertThat(playerStatisticsDto.getKills()).isEqualTo(0);
        assertThat(playerStatisticsDto.getEarnedMoney()).isEqualTo(0);
        assertThat(playerStatisticsDto.getMinedResources()).isEqualTo(0);
        assertThat(playerStatisticsDto.getTraveledDistance()).isEqualTo(0);
    }

    @Test
    void testNewPlayerStatisticsDto() {
        PlayerDto playerDto = createPlayerDto();
        int kills = 1;
        int earnedMoney = 1000;
        int minedResources = 50;
        int traveledDistance = 25;

        PlayerStatisticsDto playerStatisticsDto = new PlayerStatisticsDto(playerDto, kills, earnedMoney, minedResources, traveledDistance);
        assertThat(playerStatisticsDto.getPlayerDto()).isEqualTo(playerDto);
        assertThat(playerStatisticsDto.getKills()).isEqualTo(kills);
        assertThat(playerStatisticsDto.getEarnedMoney()).isEqualTo(earnedMoney);
        assertThat(playerStatisticsDto.getMinedResources()).isEqualTo(minedResources);
        assertThat(playerStatisticsDto.getTraveledDistance()).isEqualTo(traveledDistance);
    }

    /**
     * Create a PlayerDto for testing.
     *
     * @return new PlayerDto with test values.
     */
    private PlayerDto createPlayerDto() {
        Set<PlayerTrophyDto> playerTrophyDtos = new HashSet<>();
        TrophyDto trophyDto = new TrophyDto(1L, "FightingBronzeTrophy", "http://example.com", TrophyType.FightingBronzeTrophy);
        playerTrophyDtos.add(new PlayerTrophyDto(1L, trophyDto, UUID.randomUUID(), new Date()));
        PlayerDto playerDto = new PlayerDto(playerId, playerName, playerTrophyDtos);
        return playerDto;
    }

}
