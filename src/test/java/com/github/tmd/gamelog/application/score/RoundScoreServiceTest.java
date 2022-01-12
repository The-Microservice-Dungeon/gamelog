package com.github.tmd.gamelog.application.score;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpa;
import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpaRepository;
import com.github.tmd.gamelog.application.score.accumulator.FightingRoundScoreAccumulator;
import com.github.tmd.gamelog.application.score.accumulator.MiningRoundScoreAccumulator;
import com.github.tmd.gamelog.application.score.accumulator.MovementRoundScoreAccumulator;
import com.github.tmd.gamelog.application.score.accumulator.RobotRoundScoreAccumulator;
import com.github.tmd.gamelog.application.score.accumulator.TradingRoundScoreAccumulator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {
    RoundScoreService.class
})
class RoundScoreServiceTest {

  @MockBean
  RoundScoreJpaRepository roundScoreJpaRepository;

  @MockBean
  FightingRoundScoreAccumulator fightingRoundScoreAccumulator;

  @MockBean
  MiningRoundScoreAccumulator miningRoundScoreAccumulator;

  @MockBean
  MovementRoundScoreAccumulator movementRoundScoreAccumulator;

  @MockBean
  RobotRoundScoreAccumulator robotRoundScoreAccumulator;

  @MockBean
  TradingRoundScoreAccumulator tradingRoundScoreAccumulator;

  @Test
  void accumulateAndSaveRoundScoresForRound() {
    // Given
    var roundId = UUID.randomUUID();
    var player1 = UUID.randomUUID();
    var player2 = UUID.randomUUID();
    var player3 = UUID.randomUUID();

    var player1FightScore = mock(FightingRoundScore.class);
    when(player1FightScore.score()).thenReturn(123.0);
    var player2FightScore = mock(FightingRoundScore.class);
    when(player2FightScore.score()).thenReturn(12.5);
    var player3FightScore = mock(FightingRoundScore.class);
    when(player3FightScore.score()).thenReturn(0.0);

    var fightingRoundScores = Map.of(
        player1, player1FightScore,
        player2, player2FightScore,
        player3, player3FightScore
    );
    when(fightingRoundScoreAccumulator.accumulateRoundScores(eq(roundId))).thenReturn(fightingRoundScores);

    var player1MiningScore = mock(MiningRoundScore.class);
    when(player1MiningScore.score()).thenReturn(0.0);
    var player2MiningScore = mock(MiningRoundScore.class);
    when(player2MiningScore.score()).thenReturn(1.5);
    var player3MiningScore = mock(MiningRoundScore.class);
    when(player3MiningScore.score()).thenReturn(33.3);

    var miningRoundScores = Map.of(
        player1, player1MiningScore,
        player2, player2MiningScore,
        player3, player3MiningScore
    );
    when(miningRoundScoreAccumulator.accumulateRoundScores(eq(roundId))).thenReturn(miningRoundScores);

    var player1MovementScore = mock(MovementRoundScore.class);
    when(player1MovementScore.score()).thenReturn(12.3);
    var player2MovementScore = mock(MovementRoundScore.class);
    when(player2MovementScore.score()).thenReturn(5.3);
    var player3MovementScore = mock(MovementRoundScore.class);
    when(player3MovementScore.score()).thenReturn(7.3);

    var movementRoundScores = Map.of(
        player1, player1MovementScore,
        player2, player2MovementScore,
        player3, player3MovementScore
    );
    when(movementRoundScoreAccumulator.accumulateRoundScores(eq(roundId))).thenReturn(movementRoundScores);

    var player1RobotScore = mock(RobotRoundScore.class);
    when(player1RobotScore.score()).thenReturn(2.5);
    var player2RobotScore = mock(RobotRoundScore.class);
    when(player2RobotScore.score()).thenReturn(10.0);
    var player3RobotScore = mock(RobotRoundScore.class);
    when(player3RobotScore.score()).thenReturn(100.0);

    var robotRoundScores = Map.of(
        player1, player1RobotScore,
        player2, player2RobotScore,
        player3, player3RobotScore
    );
    when(robotRoundScoreAccumulator.accumulateRoundScores(eq(roundId))).thenReturn(robotRoundScores);

    var player1TradingScore = mock(TradingRoundScore.class);
    when(player1TradingScore.score()).thenReturn(10.0);
    var player2TradingScore = mock(TradingRoundScore.class);
    when(player2TradingScore.score()).thenReturn(10.0);
    var player3TradingScore = mock(TradingRoundScore.class);
    when(player3TradingScore.score()).thenReturn(10.0);

    var tradingRoundScores = Map.of(
        player1, player1TradingScore,
        player2, player2TradingScore,
        player3, player3TradingScore
    );
    when(tradingRoundScoreAccumulator.accumulateRoundScores(eq(roundId))).thenReturn(tradingRoundScores);

    // When
    fightingRoundScoreAccumulator.accumulateRoundScores(roundId);

    // Then
    ArgumentCaptor<RoundScoreJpa> roundScoreJpaArgumentCaptor =
        ArgumentCaptor.forClass(RoundScoreJpa.class);
    verify(roundScoreJpaRepository).save(roundScoreJpaArgumentCaptor.capture());

    var savedRoundScores = roundScoreJpaArgumentCaptor.getValue();

    assertThat(savedRoundScores.getRoundId()).isEqualTo(roundId);
    assertThat(savedRoundScores.getPlayerRoundScores()).containsOnlyKeys(player1, player2, player3);

    var player1Scores = savedRoundScores.getPlayerRoundScores().get(player1);
    var player2Scores = savedRoundScores.getPlayerRoundScores().get(player2);
    var player3Scores = savedRoundScores.getPlayerRoundScores().get(player3);

    assertThat(player1Scores.getFightingScore()).isEqualTo(123.0);
    assertThat(player2Scores.getFightingScore()).isEqualTo(12.5);
    assertThat(player3Scores.getFightingScore()).isEqualTo(0.0);

    assertThat(player1Scores.getMiningScore()).isEqualTo(0.0);
    assertThat(player2Scores.getMiningScore()).isEqualTo(1.5);
    assertThat(player3Scores.getMiningScore()).isEqualTo(33.3);

    assertThat(player1Scores.getMovementScore()).isEqualTo(12.3);
    assertThat(player2Scores.getMovementScore()).isEqualTo(5.3);
    assertThat(player3Scores.getMovementScore()).isEqualTo(7.3);

    assertThat(player1Scores.getRobotScore()).isEqualTo(2.5);
    assertThat(player2Scores.getRobotScore()).isEqualTo(10.0);
    assertThat(player3Scores.getRobotScore()).isEqualTo(100.0);

    assertThat(player1Scores.getTradingScore()).isEqualTo(10.0);
    assertThat(player2Scores.getTradingScore()).isEqualTo(10.0);
    assertThat(player3Scores.getTradingScore()).isEqualTo(10.0);
  }
}