package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.RoundScoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RoundScoreJpaRepository extends JpaRepository<RoundScoreDto, Long> {
        RoundScoreDto findByGameIdAndRoundIdAndPlayerId(String gameId, String roundId, String playerId);

        ArrayList<RoundScoreDto> findAllByRoundId(String roundId);
}
