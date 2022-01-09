package com.github.tmd.gamelog.adapter.jpa.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerDto class.
 */
public class PlayerDtoTest {

    private final UUID playerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");

    @Test
    void testEmptyNewPlayerDto() {
        PlayerDto playerDto = new PlayerDto();
        assertThat(playerDto.getId()).isNull();
        assertThat(playerDto.getEarnedTrophies()).isEmpty();
    }

    @Test
    void testNewPlayerDto() {
        PlayerDto playerDto = new PlayerDto(playerId);
        assertThat(playerDto.getId()).isEqualTo(playerId);
    }

}
