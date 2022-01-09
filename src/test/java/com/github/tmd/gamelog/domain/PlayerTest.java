package com.github.tmd.gamelog.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the Player class.
 */
public class PlayerTest {

    @Test
    void testEmptyNewPlayer() {
        // Test default values.
        Player player = new Player();
        assertThat(player.getId()).isEqualTo(null);
        assertThat(player.getEarnedTrophies()).isEmpty();

        // Test setters and getters.
        UUID playerId = UUID.randomUUID();
        Set<PlayerTrophy> earnedTrophies = new HashSet<PlayerTrophy>();
        player.setId(playerId);
        player.setEarnedTrophies(earnedTrophies);
        assertThat(player.getId()).isEqualTo(playerId);
        assertThat(player.getEarnedTrophies()).isEqualTo(earnedTrophies);
    }

    @Test
    void testNewPlayer() {
        UUID playerId = UUID.randomUUID();
        Player player = new Player(playerId);
        assertThat(player.getId()).isEqualTo(playerId);
        assertThat(player.getEarnedTrophies()).isEmpty();
    }

}
