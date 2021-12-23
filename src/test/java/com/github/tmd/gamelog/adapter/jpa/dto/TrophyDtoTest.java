package com.github.tmd.gamelog.adapter.jpa.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the TrophyDto class.
 */
public class TrophyDtoTest {

    private final long defaultId = 1;
    private final String defaultName = "First Blood";
    private TrophyDto trophyDto;

    @Test
    void testEmptyNewTrophyDto() {
        trophyDto = new TrophyDto();
        assertThat(trophyDto.getId()).isNull();
        assertThat(trophyDto.getName()).isNull();
    }

    @Test
    void testNewTrophyDto() {
        trophyDto = new TrophyDto(defaultId, defaultName);
        assertThat(trophyDto.getId()).isEqualTo(defaultId);
        assertThat(trophyDto.getName()).isEqualTo(defaultName);
    }
}