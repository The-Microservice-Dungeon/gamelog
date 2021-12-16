package com.github.tmd.gamelog.adapter.jpa;

import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import com.github.tmd.gamelog.domain.RoundScore;
import com.github.tmd.gamelog.domain.Score.MovementScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoundScoreDtoConversionUnitTest {

    private RoundScoreDtoMapper roundScoreDtoMapper;

    @BeforeEach
    void init() {
        this.roundScoreDtoMapper = new RoundScoreDtoMapper();
    }

    @Test
    public void RoundScoreToRoundScoreDto() {
        RoundScore roundScore = new RoundScore();

        roundScore.setRound(new Round("0", 0, "1"));
        roundScore.setPlayer(new Player("2"));
        MovementScore movementScore = new MovementScore();
        movementScore.increase(3);
        roundScore.setMovementScore(movementScore);

        RoundScoreDto roundScoreDto = this.roundScoreDtoMapper.mapEntityToDto(roundScore);
        assert roundScoreDto.getGame().equals("0");
        assert roundScoreDto.getRound().equals("1");
        assert roundScoreDto.getPlayer().equals("2");
        assert roundScoreDto.getMovementScore().getValue() == 3;
    }

    @Test
    public  void RoundScoreDtoToRoundScore() {
        RoundScoreDto roundScoreDto = new RoundScoreDto();
        roundScoreDto.setGame("0");
        roundScoreDto.setRound("1");
        roundScoreDto.setPlayer("2");
        MovementScoreDto movementScoreDto = new MovementScoreDto();
        movementScoreDto.setValue(3);
        roundScoreDto.setMovementScore(movementScoreDto);

        RoundScore roundScore = this.roundScoreDtoMapper.mapDtoToEntity(roundScoreDto);
        assert roundScore.getRound().getGameId().equals("0");
        assert roundScore.getRound().getRoundId().equals("1");
        assert roundScore.getPlayer().getId().equals("2");
        assert roundScore.getMovementScore().getValue() == 3;
    }
}
