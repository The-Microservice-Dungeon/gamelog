package com.github.tmd.gamelog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the Trophy class.
 */
public class TrophyTest {

    private final String defaultName = "First Blood";
    private final String defaultBadgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private Trophy trophy;

    @BeforeEach
    void beforeEach() {
        trophy = new Trophy(defaultName, defaultBadgeUrl);
    }

    @Test
    void testNewTrophy() {
        assertThat(trophy.getName()).isEqualTo(defaultName);
    }
}
