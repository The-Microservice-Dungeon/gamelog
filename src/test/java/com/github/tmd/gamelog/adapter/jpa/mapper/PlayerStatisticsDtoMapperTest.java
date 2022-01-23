package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerStatisticsDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerTrophyDto;
import com.github.tmd.gamelog.adapter.jpa.dto.TrophyDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerStatistics;
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
 * Unit Tests for the PlayerStatisticsDtoMapper class.
 */
public class PlayerStatisticsDtoMapperTest {

    private PlayerStatisticsDtoMapper playerStatisticsDtoMapper;
    private PlayerDtoMapper playerDtoMapper;

    @BeforeEach
    void beforeEach() {
        playerStatisticsDtoMapper = new PlayerStatisticsDtoMapper();
        playerDtoMapper = new PlayerDtoMapper();
    }

    @Test
    void testMapEntityToDto() {
        PlayerStatistics playerStatistics = createPlayerStatistics();
        PlayerStatisticsDto playerStatisticsDto = playerStatisticsDtoMapper.mapEntityToDto(playerStatistics);

        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(playerStatistics.getPlayer());

        assertThat(playerStatisticsDto.getPlayerDto()).isEqualTo(playerDto);
        assertThat(playerStatisticsDto.getKills()).isEqualTo(playerStatistics.getKills());
        assertThat(playerStatisticsDto.getEarnedMoney()).isEqualTo(playerStatistics.getEarnedMoney());
        assertThat(playerStatisticsDto.getMinedResources()).isEqualTo(playerStatistics.getMinedResources());
        assertThat(playerStatisticsDto.getTraveledDistance()).isEqualTo(playerStatistics.getTraveledDistance());
    }

    @Test
    void testMapDtoToEntity() {
        PlayerStatisticsDto playerStatisticsDto = createPlayerStatisticsDto();

        PlayerStatistics playerStatistics = playerStatisticsDtoMapper.mapDtoToEntity(playerStatisticsDto);

        Player player = playerDtoMapper.mapDtoToEntity(playerStatisticsDto.getPlayerDto());
        assertThat(playerStatistics.getPlayer()).isEqualTo(player);
        assertThat(playerStatistics.getKills()).isEqualTo(playerStatisticsDto.getKills());
        assertThat(playerStatistics.getEarnedMoney()).isEqualTo(playerStatisticsDto.getEarnedMoney());
        assertThat(playerStatistics.getMinedResources()).isEqualTo(playerStatisticsDto.getMinedResources());
        assertThat(playerStatistics.getTraveledDistance()).isEqualTo(playerStatisticsDto.getTraveledDistance());
    }

    /**
     * Create a PlayerStatistics object for testing.
     *
     * @return new PlayerStatistics with test values.
     */
    private PlayerStatistics createPlayerStatistics() {
        Set<PlayerTrophy> playerTrophies = new HashSet<>();
        playerTrophies.add(new PlayerTrophy(new FightingBronzeTrophy(), UUID.randomUUID(), new Date()));
        Player player = new Player(UUID.randomUUID(), "Max Mustermann", playerTrophies);
        PlayerStatistics playerStatistics = new PlayerStatistics(player, 1, 1000, 50, 25);
        return playerStatistics;
    }

    /**
     * Create a PlayerStatisticsDto object for testing.
     *
     * @return new PlayerStatisticsDto with test values.
     */
    private PlayerStatisticsDto createPlayerStatisticsDto() {
        Set<PlayerTrophyDto> playerTrophyDtos = new HashSet<>();
        TrophyDto trophyDto = new TrophyDto(1L, "FightingBronzeTrophy", "https://example.com", TrophyType.FightingBronzeTrophy);
        playerTrophyDtos.add(new PlayerTrophyDto(1L, trophyDto, UUID.randomUUID(), new Date()));
        PlayerDto playerDto = new PlayerDto(UUID.randomUUID(), "Max Mustermann", playerTrophyDtos);
        PlayerStatisticsDto playerStatisticsDto = new PlayerStatisticsDto(playerDto, 1, 1000, 50, 25);
        return playerStatisticsDto;
    }

}
