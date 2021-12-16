package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.*;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoundScoreRepositoryTest {

    private RoundScoreRepository roundScoreRepository;

    @Mock
    private RoundScoreJpaRepository roundScoreJpaRepository;

    @BeforeEach
    public void init() {
        roundScoreRepository = new RoundScoreRepository(roundScoreJpaRepository, new RoundScoreDtoMapper());
    }


    @Test
    void testFindByGameAndRoundAndPlayerNotExisting() {
        CommandContext commandContext = this.createGameContext();

        Mockito.when(roundScoreJpaRepository.findByGameIdAndRoundIdAndPlayerId("1", "2", "3")).thenReturn(null);
        RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
        assert roundScore == null;
    }

    @Test
    void testFindByGameAndRoundAndPlayerExisting() {
        CommandContext commandContext = this.createGameContext();

        RoundScoreDto mockRoundScoreDto = new RoundScoreDto();
        mockRoundScoreDto.setGameId("1");
        mockRoundScoreDto.setRoundId("2");
        mockRoundScoreDto.setPlayerId("3");
        MovementScoreDto movementScoreDto = new MovementScoreDto();
        movementScoreDto.setValue(1);
        mockRoundScoreDto.setMovementScore(movementScoreDto);

        Mockito
            .when(this.roundScoreJpaRepository.findByGameIdAndRoundIdAndPlayerId("1", "2", "3"))
            .thenReturn(mockRoundScoreDto);

        RoundScore roundScore = this.roundScoreRepository.findByCommandContext(commandContext);
        System.out.println(roundScore);
        System.out.println("SCORE: " + roundScore.getMovementScore());
        assert roundScore.getMovementScore().getValue() == 1;
    }

    private CommandContext createGameContext() {
        CommandContext commandContext = new CommandContext();
        commandContext.setRound(new Round("1", 0, "2"));
        commandContext.setPlayer(new Player("3"));

        return commandContext;
    }
}
