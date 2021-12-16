package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.RoundScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RoundScoreJpaRepository extends JpaRepository<RoundScoreDto, Long> {
        RoundScoreDto findByGameAndRoundAndPlayer(String game, String round, String player);

        ArrayList<RoundScoreDto> findAllByRoundId(String roundId);
}
