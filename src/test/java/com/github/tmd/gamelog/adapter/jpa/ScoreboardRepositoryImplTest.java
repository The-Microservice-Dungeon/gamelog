package com.github.tmd.gamelog.adapter.jpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.score.repository.ScoreboardRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:db/mock_history.sql", "classpath:db/mock_scores.sql"})
class ScoreboardRepositoryImplTest {

  @Autowired
  ScoreboardRepositoryImpl scoreboardRepository;

  @Test
  void getScoreboardByGameId() {
    // Given
    var rawId = UUID.fromString("22838d5f-3e11-46fd-9074-d76d0e2ad27c");
    var gameId = new GameId(rawId);

    // When
    var optScoreboard = this.scoreboardRepository.getScoreboardByGameId(gameId);

    // Then
    // For the sake of simplicity and because the score calculation might change, we're just
    // testing trivial
    assertThat(optScoreboard).isNotEmpty();
    var scoreboard = optScoreboard.get();
    assertThat(scoreboard.getGame().getId()).isEqualTo(gameId);
    assertThat(scoreboard.getGameScores()).hasSize(3);
    assertThat(scoreboard.getRoundScores()).hasSize(3);
  }
}