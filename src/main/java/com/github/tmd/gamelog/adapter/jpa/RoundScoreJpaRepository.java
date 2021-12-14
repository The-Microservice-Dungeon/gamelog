package com.github.tmd.gamelog.adapter.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundScoreJpaRepository extends JpaRepository<RoundScoreDto, Long> {
        RoundScoreDto findByGameAndRoundAndPlayer(String game, String round, String player);
}
