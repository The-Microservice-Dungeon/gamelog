package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerDtoMapper class.
 */
public class PlayerDtoMapperTest {

    private final String playerId = "c6dcbdac-be0b-4de0-b50d-7870caa5f744";

    private PlayerDtoMapper playerDtoMapper;

    @BeforeEach
    void beforeEach() {
        playerDtoMapper = new PlayerDtoMapper();
    }

    @Test
    void testMapEntityToDto() {
        Player player = new Player(playerId);
        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(player);
        assertThat(playerDto.getId()).isEqualTo(playerId);
    }

    @Test
    void testMapDtoToEntity() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(playerId);
        Player player = playerDtoMapper.mapDtoToEntity(playerDto);
        assertThat(player.getId()).isEqualTo(playerId);
    }

}
