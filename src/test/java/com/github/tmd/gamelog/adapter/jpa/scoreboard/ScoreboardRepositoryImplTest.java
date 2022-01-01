package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpa;
import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpaRepository;
import com.github.tmd.gamelog.domain.player.Player;
import com.github.tmd.gamelog.domain.player.Player.PlayerId;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard.ScoreboardId;
import com.github.tmd.gamelog.domain.scoreboard.model.Game;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.Round;
import com.github.tmd.gamelog.domain.scoreboard.model.Round.RoundId;
import com.github.tmd.gamelog.domain.scoreboard.model.RoundScore;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("h2")
class ScoreboardRepositoryImplTest {

  @Autowired
  ScoreboardRepositoryImpl scoreboardRepository;

  @Autowired
  PlayerJpaRepository playerJpaRepository;

  @Test
  void save() {
    // Given
    var player1Id = UUID.randomUUID();
    var player1Jpa = new PlayerJpa(player1Id, "player1");
    var player2Id = UUID.randomUUID();
    var player2Jpa = new PlayerJpa(player2Id, "player2");
    playerJpaRepository.save(player1Jpa);
    playerJpaRepository.save(player2Jpa);

    var player1 = new Player(new PlayerId(player1Id), "player1");
    var player2 = new Player(new PlayerId(player2Id), "player2");

    var round1 = new Round(new RoundId(UUID.fromString("9656fbc9-8c14-4aa5-bf78-2e1824e67120")), 0);
    var round2 = new Round(new RoundId(UUID.fromString("bd2b5a37-2ed1-430d-a0ca-487e83c2abc9")), 1);

    var givenRounds = Set.of(round1, round2);
    var givenGame = new Game(new GameId(UUID.fromString("f42af39f-840b-40c8-86d7-d54cab6f15dd")), givenRounds);

    var scores = Map.of(
        player1, Set.of(new RoundScore(round1), new RoundScore(round2)),
        player2, Set.of(new RoundScore(round1), new RoundScore(round2))
    );

    var givenScoreboard = new Scoreboard(
        new ScoreboardId(UUID.fromString("dbc5be1d-d75a-4cc4-b536-f132a57028c6")),
        givenGame,
        scores);

    // When
    var scoreboard = scoreboardRepository.save(givenScoreboard);

    // Then
    assertThat(scoreboard).isNotNull();
    assertThat(scoreboard.getScoreboardId().id()).isEqualTo(UUID.fromString("dbc5be1d-d75a-4cc4-b536-f132a57028c6"));
  }

  @Test
  void getScoreboardByGameId() {
  }
}