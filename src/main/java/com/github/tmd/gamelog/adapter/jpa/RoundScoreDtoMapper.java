package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.adapter.jpa.dto.MovementScoreDto;
import com.github.tmd.gamelog.adapter.jpa.dto.RoundScoreDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import com.github.tmd.gamelog.domain.RoundScore;
import com.github.tmd.gamelog.domain.Score.MovementScore;
import org.springframework.stereotype.Component;

@Component
public class RoundScoreDtoMapper {
    public RoundScore mapDtoToEntity(RoundScoreDto roundScoreDto) {
        RoundScore roundScore = new RoundScore();

        roundScore.setRound(
            new Round(
                roundScoreDto.getGameId(),
                roundScoreDto.getRoundNumber(),
                roundScoreDto.getRoundId()
            )
        );

        roundScore.setPlayer(
            new Player(roundScoreDto.getPlayerId())
        );

        MovementScore movementScore = new MovementScore();
        movementScore.setValue(roundScoreDto.getMovementScore().getValue());
        roundScore.setMovementScore(movementScore);

        return roundScore;
    }

    public RoundScoreDto mapEntityToDto(RoundScore roundScore) {
        RoundScoreDto roundScoreDto = new RoundScoreDto();

        roundScoreDto.setGameId(roundScore.getRound().getGameId());
        roundScoreDto.setRoundNumber(roundScore.getRound().getRoundNumber());
        roundScoreDto.setRoundId(roundScore.getRound().getRoundId());
        roundScoreDto.setPlayerId(roundScore.getPlayer().getId());
        MovementScoreDto movementScoreDto = new MovementScoreDto();
        movementScoreDto.setValue(roundScore.getMovementScore().getValue());
        roundScoreDto.setMovementScore(movementScoreDto);

        return roundScoreDto;
    }
}
