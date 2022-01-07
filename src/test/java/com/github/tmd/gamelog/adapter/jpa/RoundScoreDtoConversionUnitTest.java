package com.github.tmd.gamelog.adapter.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tmd.gamelog.adapter.jpa.dto.MovementScoreDto;
import com.github.tmd.gamelog.adapter.jpa.dto.RoundScoreDto;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.Round;
import com.github.tmd.gamelog.domain.RoundScore;
import com.github.tmd.gamelog.domain.Score.MovementScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RoundScoreDtoConversionUnitTest {

    private RoundScoreDtoMapper roundScoreDtoMapper;

    private UUID playerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");

    @BeforeEach
    void init() {
        this.roundScoreDtoMapper = new RoundScoreDtoMapper();
    }

    @Test
    public void RoundScoreToRoundScoreDto() {
        RoundScore roundScore = new RoundScore();

        roundScore.setRound(new Round("0", 0, "1"));
        roundScore.setPlayer(new Player(playerId));
        MovementScore movementScore = new MovementScore();
        movementScore.increase(3);
        roundScore.setMovementScore(movementScore);

        RoundScoreDto roundScoreDto = this.roundScoreDtoMapper.mapEntityToDto(roundScore);
        assertThat(roundScoreDto.getGameId()).isEqualTo("0");
        assertThat(roundScoreDto.getRoundId()).isEqualTo("1");
        assertThat(roundScoreDto.getPlayerId()).isEqualTo(playerId);
        assertThat(roundScoreDto.getMovementScore().getValue()).isEqualTo(3);
    }

    @Test
    public  void RoundScoreDtoToRoundScore() {
        RoundScoreDto roundScoreDto = new RoundScoreDto();
        roundScoreDto.setGameId("0");
        roundScoreDto.setRoundId("1");
        roundScoreDto.setPlayerId(playerId);
        MovementScoreDto movementScoreDto = new MovementScoreDto();
        movementScoreDto.setValue(3);
        roundScoreDto.setMovementScore(movementScoreDto);

        RoundScore roundScore = this.roundScoreDtoMapper.mapDtoToEntity(roundScoreDto);
        assertThat(roundScore.getRound().getGameId()).isEqualTo("0");
        assertThat(roundScore.getRound().getRoundId()).isEqualTo("1");
        assertThat(roundScore.getPlayer().getId()).isEqualTo(playerId);
        assertThat(roundScore.getMovementScore().getValue()).isEqualTo(3);
    }
}
