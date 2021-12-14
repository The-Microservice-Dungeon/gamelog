package com.github.tmd.gamelog.domain;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreJpaRepository;
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
        roundScoreRepository = new RoundScoreRepository(roundScoreJpaRepository);
    }


    @Test
    void testFindByGameAndRoundAndPlayerNotExisting() {
        CommandContext commandContext = new CommandContext();
        commandContext.setGameId("1");
        commandContext.setRoundId("2");
        commandContext.setPlayerId("3");
        Mockito.when(roundScoreJpaRepository.findByGameAndRoundAndPlayer("1", "2", "3")).thenReturn(null);
        RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
        assert roundScore.getMovementScore() == 0;
    }

    @Test
    void testFindByGameAndRoundAndPlayerExisting() {
        CommandContext commandContext = new CommandContext();
        commandContext.setGameId("1");
        commandContext.setRoundId("2");
        commandContext.setPlayerId("3");

        RoundScoreDto mockRoundScoreDto = new RoundScoreDto();
        mockRoundScoreDto.setMovementScore(1);
        Mockito.when(roundScoreJpaRepository.findByGameAndRoundAndPlayer("1", "2", "3")).thenReturn(mockRoundScoreDto);
        RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
        assert roundScore.getMovementScore() == 1;
    }
}
