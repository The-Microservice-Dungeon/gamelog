package com.github.tmd.gamelog.adapter.jpa.mapper;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerStatisticsDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import org.springframework.stereotype.Component;

/**
 * Class for mapping PlayerStatisticsDto to PlayerStatistics and vice-versa.
 */
@Component
public class PlayerStatisticsDtoMapper {

    private final PlayerDtoMapper playerDtoMapper;

    public PlayerStatisticsDtoMapper() {
        playerDtoMapper = new PlayerDtoMapper();
    }

    /**
     * Map a PlayerStatistics object to PlayerStatisticsDto.
     * @param playerStatistics PlayerStatistics object to map.
     * @return PlayerStatisticsDto that matches the input object.
     */
    public PlayerStatisticsDto mapEntityToDto(PlayerStatistics playerStatistics) {
        PlayerDto playerDto = playerDtoMapper.mapEntityToDto(playerStatistics.getPlayer());
        PlayerStatisticsDto playerStatisticsDto = new PlayerStatisticsDto(
                playerDto,
                playerStatistics.getKills(),
                playerStatistics.getEarnedMoney(),
                playerStatistics.getMinedResources(),
                playerStatistics.getTraveledDistance()
        );
        return playerStatisticsDto;
    }

    /**
     * Map a PlayerStatisticsDto object to PlayerStatistics.
     * @param playerStatisticsDto PlayerStatisticsDto object to map.
     * @return PlayerStatistics that matches the input object.
     */
    public PlayerStatistics mapDtoToEntity(PlayerStatisticsDto playerStatisticsDto) {
        Player player = playerDtoMapper.mapDtoToEntity(playerStatisticsDto.getPlayerDto());
        PlayerStatistics playerStatistics = new PlayerStatistics(
                player,
                playerStatisticsDto.getKills(),
                playerStatisticsDto.getEarnedMoney(),
                playerStatisticsDto.getMinedResources(),
                playerStatisticsDto.getTraveledDistance()
        );
        return playerStatistics;
    }

}
