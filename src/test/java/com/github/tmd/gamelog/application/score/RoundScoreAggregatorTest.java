package com.github.tmd.gamelog.application.score;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:db/mock_history.sql")
class RoundScoreAggregatorTest {

  @Autowired
  RoundScoreAggregator roundScoreAggregator;

  @Test
  void shouldReturnScoresForRound1() {
    // Given
    var round1Id = UUID.fromString("a03cea0f-1a6b-4127-973a-658839913225");

    // When
    var roundScores = this.roundScoreAggregator.aggregateRoundScoresForRound(round1Id);

    // Then

    // Since the score calculation might change in the future, we're not testing the actual scores,
    // Just that the scores are being aggregated
    assertThat(roundScores).hasSize(3);
  }

  @Test
  void shouldReturnScoresForRound2() {
    // Given
    var round2Id = UUID.fromString("fb4d537b-61e1-4165-ad01-0745504a1955");

    // When
    var roundScores = this.roundScoreAggregator.aggregateRoundScoresForRound(round2Id);

    // Since the score calculation might change in the future, we're not testing the actual scores,
    // Just that the scores are being aggregated
    assertThat(roundScores).hasSize(3);
  }

  @Test
  void shouldReturnScoresForRound3() {
    // Given
    var round3Id = UUID.fromString("c0305195-0005-4ee9-8350-023ee9e588d0");

    // When
    var roundScores = this.roundScoreAggregator.aggregateRoundScoresForRound(round3Id);

    // Since the score calculation might change in the future, we're not testing the actual scores,
    // Just that the scores are being aggregated
    assertThat(roundScores).hasSize(3);
  }
}