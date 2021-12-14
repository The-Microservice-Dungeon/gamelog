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
        RoundScoreDto roundScoreDto = roundScoreJpsRepository.findByGameAndRoundAndPlayer(gameId, roundId, playerId);
        if (roundScoreDto == null) {
            RoundScore roundScore = new RoundScore();
            roundScore.setGame(gameId);
            roundScore.setRound(roundId);
            roundScore.setPlayer(playerId);
            return roundScore;
        }
        return RoundScore.fromRoundScoreDto(roundScoreDto);
    }

    @Override
    public void save(RoundScore roundScore) {
        RoundScoreDto existing = roundScoreJpsRepository.findByGameAndRoundAndPlayer(roundScore.getGame(), roundScore.getRound(), roundScore.getPlayer());
        if (existing != null) {
            existing.setMovementScore(roundScore.getMovementScore());
            roundScoreJpsRepository.save(existing);
        } else {
            RoundScoreDto roundScoreDto = RoundScoreDto.fromRoundScore(roundScore);
            this.roundScoreJpsRepository.save(roundScoreDto);
        }
    }
}