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
        CommandContext commandContext = this.createGameContext();

        Mockito.when(roundScoreJpaRepository.findByGameAndRoundAndPlayer("1", "2", "3")).thenReturn(null);
        RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
        assert roundScore.getMovementScore() == 0;
    }

    @Test
    void testFindByGameAndRoundAndPlayerExisting() {
        CommandContext commandContext = this.createGameContext();

        RoundScoreDto mockRoundScoreDto = new RoundScoreDto();
        mockRoundScoreDto.setMovementScore(1);
        Mockito.when(roundScoreJpaRepository.findByGameAndRoundAndPlayer("1", "2", "3")).thenReturn(mockRoundScoreDto);
        RoundScore roundScore = roundScoreRepository.findByCommandContext(commandContext);
        assert roundScore.getMovementScore() == 1;
    }

    private CommandContext createGameContext() {
        CommandContext commandContext = new CommandContext();
        commandContext.setPlayer(new Player("3"));
        commandContext.setRound(new Round("1", 0, "2"));

        return commandContext;
    }
}
