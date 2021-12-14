package com.github.tmd.gamelog;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreDto;
import com.github.tmd.gamelog.adapter.jpa.RoundScoreJpsRepository;

import com.github.tmd.gamelog.adapter.jpa.RoundScoreRepository;
import com.github.tmd.gamelog.domain.RoundScore;
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
    private RoundScoreJpsRepository roundScoreJpsRepository;

    @BeforeEach
    public void init(){
        roundScoreRepository = new RoundScoreRepository(roundScoreJpsRepository);
    }


    @Test
    void testFindByGameAndRoundAndPlayerNotExisting() {
        Mockito.when(roundScoreJpsRepository.findByGameAndRoundAndPlayer("1", "2", "3")).thenReturn(null);
        RoundScore roundScore = roundScoreRepository.findByGameAndRoundAndPlayer("1", "2", "3");
        assert roundScore.getMovementScore() == 0;
    }

    @Test
    void testFindByGameAndRoundAndPlayerExisting() {
        RoundScoreDto mockRoundScoreDto = new RoundScoreDto();
        mockRoundScoreDto.setMovementScore(1);
        Mockito.when(roundScoreJpsRepository.findByGameAndRoundAndPlayer("1", "2", "3")).thenReturn(mockRoundScoreDto);
        RoundScore roundScore = roundScoreRepository.findByGameAndRoundAndPlayer("1", "2", "3");
        assert roundScore.getMovementScore() == 1;
    }
}
