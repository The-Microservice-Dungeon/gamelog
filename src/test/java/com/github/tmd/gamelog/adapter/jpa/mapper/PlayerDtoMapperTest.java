package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerTrophy;
import com.github.tmd.gamelog.domain.trophies.TrophyType;
import com.github.tmd.gamelog.domain.trophies.achievements.FightingBronzeTrophy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Tests for the PlayerDtoMapper class.
 */
public class PlayerDtoMapperTest {

    private PlayerDtoMapper playerDtoMapper;

    @BeforeEach
    void beforeEach() {
        playerDtoMapper = new PlayerDtoMapper();
    }

    @Test
    void testMapEntityToDto() {
        UUID playerId = UUID.randomUUID();
        Set<PlayerTrophy> earnedTrophies = new HashSet<>();
        earnedTrophies.add(new PlayerTrophy(new FightingBronzeTrophy(), UUID.randomUUID(), new Date()));
        Player player = new Player(playerId, Player.UNKNOWN_QUALIFIER, earnedTrophies);
        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(player);
        assertThat(playerDto.getId()).isEqualTo(playerId);
        assertThat(playerDto.getEarnedTrophies().size()).isEqualTo(earnedTrophies.size());
        assertThat(playerDto.getName()).isEqualTo(Player.UNKNOWN_QUALIFIER);
    }

    @Test
    void testMapDtoToEntity() {
        UUID playerId = UUID.randomUUID();
        Set<PlayerTrophyDto> earnedTrophies = new HashSet<>();
        earnedTrophies.add(new PlayerTrophyDto(1L, new TrophyDto(1L, "", "", TrophyType.Trophy), UUID.randomUUID(), new Date()));
        PlayerDto playerDto = new PlayerDto(playerId, Player.UNKNOWN_QUALIFIER, earnedTrophies);
        Player player = playerDtoMapper.mapDtoToEntity(playerDto);
        assertThat(player.getId()).isEqualTo(playerId);
        assertThat(player.getEarnedTrophies().size()).isEqualTo(earnedTrophies.size());
        assertThat(player.getName()).isEqualTo(Player.UNKNOWN_QUALIFIER);
    }

}
