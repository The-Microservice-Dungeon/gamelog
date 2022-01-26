package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerStatisticsDto;
import com.github.tmd.gamelog.adapter.jpa.mapper.PlayerStatisticsDtoMapper;
import com.github.tmd.gamelog.domain.PlayerStatistics;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Repository for objects of class PlayerStatistics.
 */
@Repository
public class PlayerStatisticsRepositoryImpl {

    private PlayerStatisticsJpaRepository playerStatisticsJpaRepository;
    private PlayerStatisticsDtoMapper playerStatisticsDtoMapper;

    public PlayerStatisticsRepositoryImpl(PlayerStatisticsJpaRepository playerStatisticsJpaRepository, PlayerStatisticsDtoMapper playerStatisticsDtoMapper) {
        this.playerStatisticsJpaRepository = playerStatisticsJpaRepository;
        this.playerStatisticsDtoMapper = playerStatisticsDtoMapper;
    }

    public Set<PlayerStatistics> getPlayerStatisticsForGame(UUID gameId) {
        Set<PlayerStatisticsDto> playerStatisticsDtos = playerStatisticsJpaRepository.getPlayerStatisticsForGame(gameId);
        Set<PlayerStatistics> playerStatistics = new HashSet<>();
        for (PlayerStatisticsDto playerStatisticsDto :
                playerStatisticsDtos) {
            playerStatistics.add(playerStatisticsDtoMapper.mapDtoToEntity(playerStatisticsDto));
        }
        return playerStatistics;
    }

}
