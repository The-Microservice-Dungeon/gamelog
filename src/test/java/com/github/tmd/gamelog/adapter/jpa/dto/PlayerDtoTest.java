package com.github.tmd.gamelog.adapter.jpa.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerDto class.
 */
public class PlayerDtoTest {

    private final String id = "c6dcbdac-be0b-4de0-b50d-7870caa5f744";

    @Test
    void testEmptyNewPlayerDto() {
        PlayerDto playerDto = new PlayerDto();
        assertThat(playerDto.getId()).isNull();
    }

    @Test
    void testNewPlayerDto() {
        PlayerDto playerDto = new PlayerDto(id);
        assertThat(playerDto.getId()).isEqualTo(id);
    }

}
