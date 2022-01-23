package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.trophies.achievements.FightingBronzeTrophy;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerStatistics class.
 */
public class PlayerStatisticsTest {

    @Test
    void testEmptyNewPlayerStatistics() {
        PlayerStatistics playerStatistics = new PlayerStatistics();

        assertThat(playerStatistics.getPlayer().getId()).isNull();
        assertThat(playerStatistics.getPlayer().getName()).isNotEmpty();
        assertThat(playerStatistics.getPlayer().getEarnedTrophies()).isEmpty();
        assertThat(playerStatistics.getKills()).isEqualTo(0);
        assertThat(playerStatistics.getEarnedMoney()).isEqualTo(0);
        assertThat(playerStatistics.getMinedResources()).isEqualTo(0);
        assertThat(playerStatistics.getTraveledDistance()).isEqualTo(0);
    }

    @Test
    void testNewPlayerStatistics() {
        Player player = createPlayer();
        int kills = 1;
        int earnedMoney = 1000;
        int minedResources = 50;
        int traveledDistance = 25;

        PlayerStatistics playerStatistics = new PlayerStatistics(player, kills, earnedMoney, minedResources, traveledDistance);

        assertThat(playerStatistics.getPlayer()).isEqualTo(player);
        assertThat(playerStatistics.getKills()).isEqualTo(kills);
        assertThat(playerStatistics.getEarnedMoney()).isEqualTo(earnedMoney);
        assertThat(playerStatistics.getMinedResources()).isEqualTo(minedResources);
        assertThat(playerStatistics.getTraveledDistance()).isEqualTo(traveledDistance);
    }

    /**
     * Create a Player object for testing.
     *
     * @return new Player with test values.
     */
    private Player createPlayer() {
        Set<PlayerTrophy> playerTrophies = new HashSet<>();
        playerTrophies.add(new PlayerTrophy(new FightingBronzeTrophy(), UUID.randomUUID(), new Date()));
        Player player = new Player(UUID.randomUUID(), "Max Mustermann", playerTrophies);
        return player;
    }

}