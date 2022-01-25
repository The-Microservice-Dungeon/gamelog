package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.PlayerDto;
import com.github.tmd.gamelog.adapter.jpa.dto.PlayerStatisticsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

/**
 * JpaRepository for PlayerStatisticsDto objects.
 */
@Repository
public interface PlayerStatisticsJpaRepository extends JpaRepository<PlayerDto, UUID> {

    @Query("SELECT new com.github.tmd.gamelog.adapter.jpa.dto.PlayerStatisticsDto(p, 0, 0, 0, 0) from PlayerDto p")
    Set<PlayerStatisticsDto> getPlayerStatisticsForGame(UUID gameId);

}
