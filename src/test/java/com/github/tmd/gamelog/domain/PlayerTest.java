package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.domain.trophies.Trophy;
import com.github.tmd.gamelog.domain.trophies.achievements.FightingBronzeTrophy;
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

    @Test
    void testTrophyAlreadyEarned(){
        UUID gameId = UUID.randomUUID();
        Trophy trophy = new FightingBronzeTrophy();
        Player player = new Player(UUID.randomUUID());

        assertThat(player.trophyAlreadyEarned(trophy, gameId)).isFalse();

        player.awardTrophy(trophy, gameId);
        assertThat(player.trophyAlreadyEarned(trophy, gameId)).isTrue();

        int numberOfPlayerTrophies = player.getEarnedTrophies().size();

        // Wait a millisecond so the date generated in awardTrophy isn't the same.
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player.awardTrophy(trophy, gameId);
        assertThat(player.getEarnedTrophies().size()).isEqualTo(numberOfPlayerTrophies);
    }

}
