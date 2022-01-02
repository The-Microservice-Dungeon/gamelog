package com.github.tmd.gamelog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the Trophy class.
 */
public class TrophyTest {

    private final String defaultName = "First Blood";
    private Trophy trophy;

    @BeforeEach
    void beforeEach() {
        trophy = new Trophy(defaultName);
    }

    @Test
    void testNewTrophy() {
        assertThat(trophy.getName()).isEqualTo(defaultName);
    }
}
