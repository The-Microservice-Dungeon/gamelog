package com.github.tmd.gamelog;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.domain.RoundScore;
import org.junit.jupiter.api.Test;

public class RoundScoreConversionUnitTest {

    @Test
    public void RoundScoreToRoundScoreDto() {
        RoundScore roundScore = new RoundScore();
        roundScore.setGame("0");
        roundScore.setRound("1");
        roundScore.setPlayer("2");
        roundScore.setMovementScore(3);

        RoundScoreDto roundScoreDto = RoundScoreDto.fromRoundScore(roundScore);
        assert roundScoreDto.getGame().equals("0");
        assert roundScoreDto.getRound().equals("1");
        assert roundScoreDto.getPlayer().equals("2");
        assert roundScoreDto.getMovementScore() == 3;
    }

    @Test
    public  void RoundScoreDtoToRoundScore() {
        RoundScoreDto roundScoreDto = new RoundScoreDto();
        roundScoreDto.setGame("0");
        roundScoreDto.setRound("1");
        roundScoreDto.setPlayer("2");
        roundScoreDto.setMovementScore(3);

        RoundScore roundScore = RoundScore.fromRoundScoreDto(roundScoreDto);
        assert roundScore.getGame().equals("0");
        assert roundScore.getRound().equals("1");
        assert roundScore.getPlayer().equals("2");
        assert roundScore.getMovementScore() == 3;
    }
}
