package com.github.tmd.gamelog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the Trophy class.
 */
public class TrophyTest {

    Trophy trophy;

    private final int defaultId = 1;
    private final String defaultName = "First Blood";

    @BeforeEach
    void beforeEach() {
        trophy = new Trophy(defaultId, defaultName);
    }

    @Test
    void testNewTrophy(){
        assertThat(trophy.getId()).isEqualTo(defaultId);
        assertThat(trophy.getName()).isEqualTo(defaultName);
    }
}
