package com.github.tmd.gamelog.adapter.jpa.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the TrophyDto class.
 */
public class TrophyDtoTest {

    private final long defaultId = 1;
    private final String defaultName = "First Blood";
    private final String defaultBadgeUrl = "https://raw.githubusercontent.com/wiki/The-Microservice-Dungeon/gamelog/assets/pictures/trophies/achievements/Fighting%20Bronze%20-%20First%20Blood.png";
    private TrophyDto trophyDto;

    @Test
    void testEmptyNewTrophyDto() {
        trophyDto = new TrophyDto();
        assertThat(trophyDto.getId()).isNull();
        assertThat(trophyDto.getName()).isNull();
    }

    @Test
    void testNewTrophyDto() {
        trophyDto = new TrophyDto(defaultId, defaultName, defaultBadgeUrl);
        assertThat(trophyDto.getId()).isEqualTo(defaultId);
        assertThat(trophyDto.getName()).isEqualTo(defaultName);
    }
}
