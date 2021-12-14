package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.CommandContext;
import com.github.tmd.gamelog.domain.RoundScore;
import org.springframework.stereotype.Component;

@Component
public class RoundScoreRepository implements com.github.tmd.gamelog.domain.RoundScoreRepository {

    RoundScoreJpaRepository roundScoreJpaRepository;

    public RoundScoreRepository(RoundScoreJpaRepository roundScoreJpaRepository) {
        this.roundScoreJpaRepository = roundScoreJpaRepository;
    }

    public RoundScore findByCommandContext(CommandContext commandContext) {
        RoundScoreDto roundScoreDto = roundScoreJpaRepository.findByGameAndRoundAndPlayer(
                commandContext.getRound().getGameId(),
                commandContext.getRound().getRoundId(),
                commandContext.getPlayer().getId()
        );

        if (roundScoreDto == null) {
            System.out.println("NULL");
            RoundScore roundScore = new RoundScore();
            roundScore.setGame(commandContext.getRound().getGameId());
            roundScore.setRound(commandContext.getRound().getRoundId());
            roundScore.setPlayer(commandContext.getPlayer().getId());

            return roundScore;
        }

        return RoundScore.fromRoundScoreDto(roundScoreDto);
    }

    public void save(RoundScore roundScore) {
        RoundScoreDto existing = roundScoreJpaRepository.findByGameAndRoundAndPlayer(roundScore.getGame(), roundScore.getRound(), roundScore.getPlayer());
        if (existing != null) {
            existing.setMovementScore(roundScore.getMovementScore());
            roundScoreJpaRepository.save(existing);
        } else {
            RoundScoreDto roundScoreDto = RoundScoreDto.fromRoundScore(roundScore);
            this.roundScoreJpaRepository.save(roundScoreDto);
        }
    }
}
