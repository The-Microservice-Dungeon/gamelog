package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.RoundScore;
import org.springframework.stereotype.Component;

@Component
public class RoundScoreRepository implements com.github.tmd.gamelog.domain.RoundScoreRepository {

    RoundScoreJpsRepository roundScoreJpsRepository;

    public RoundScoreRepository(RoundScoreJpsRepository roundScoreJpsRepository) {
        this.roundScoreJpsRepository = roundScoreJpsRepository;
    }

    @Override
    public RoundScore findByGameAndRoundAndPlayer(String gameId, String roundId, String playerId) {
        return null;
    }

    @Override
    public void save(RoundScore roundScore) {
        RoundScoreDto roundScoreDto = RoundScoreDto.fromRoundScore(roundScore);
        this.roundScoreJpsRepository.save(roundScoreDto);
    }
}
