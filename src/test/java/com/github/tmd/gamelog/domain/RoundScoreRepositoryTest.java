package com.github.tmd.gamelog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.adapter.jpa.dto.MovementScoreDto;
import com.github.tmd.gamelog.adapter.jpa.dto.RoundScoreDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RoundScoreRepositoryTest {

    private RoundScoreRepository roundScoreRepository;

    @Mock
    private RoundScoreDtoRepository roundScoreDtoRepository;

    private UUID playerId = UUID.fromString("c6dcbdac-be0b-4de0-b50d-7870caa5f744");

    @BeforeEach
    public void init() {
        roundScoreRepository = new RoundScoreRepository(roundScoreDtoRepository, new RoundScoreDtoMapper());
    }


    @Test
    void testFindByGameAndRoundAndPlayerNotExisting() {
        CommandContext commandContext = this.createGameContext();

        Mockito.when(roundScoreDtoRepository.findByGameIdAndRoundIdAndPlayerId("1", "2", playerId)).thenReturn(null);
        RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
        assertThat(roundScore).isNull();
    }

    @Test
    void testFindByGameAndRoundAndPlayerExisting() {
        CommandContext commandContext = this.createGameContext();

        RoundScoreDto mockRoundScoreDto = new RoundScoreDto();
        mockRoundScoreDto.setGameId("1");
        mockRoundScoreDto.setRoundId("2");
        mockRoundScoreDto.setPlayerId(playerId);
        MovementScoreDto movementScoreDto = new MovementScoreDto();
        movementScoreDto.setValue(1);
        mockRoundScoreDto.setMovementScore(movementScoreDto);

        Mockito
            .when(this.roundScoreDtoRepository.findByGameIdAndRoundIdAndPlayerId("1", "2", playerId))
            .thenReturn(mockRoundScoreDto);

        RoundScore roundScore = this.roundScoreRepository.findByCommandContext(commandContext);
        System.out.println(roundScore);
        System.out.println("SCORE: " + roundScore.getMovementScore());
        assertThat(roundScore.getMovementScore().getValue()).isEqualTo(1);
    }

    private CommandContext createGameContext() {
        CommandContext commandContext = new CommandContext();
        commandContext.setRound(new Round("1", 0, "2"));
        commandContext.setPlayer(new Player(playerId));

        return commandContext;
    }
}
