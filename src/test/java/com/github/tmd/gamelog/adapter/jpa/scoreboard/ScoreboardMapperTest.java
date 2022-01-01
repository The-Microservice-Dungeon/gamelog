package com.github.tmd.gamelog.adapter.jpa.scoreboard;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.github.tmd.gamelog.adapter.jpa.player.PlayerJpa;
import com.github.tmd.gamelog.domain.player.Player;
import com.github.tmd.gamelog.domain.player.Player.PlayerId;
import com.github.tmd.gamelog.domain.scoreboard.model.Game;
import com.github.tmd.gamelog.domain.scoreboard.model.Game.GameId;
import com.github.tmd.gamelog.domain.scoreboard.model.GameStatus;
import com.github.tmd.gamelog.domain.scoreboard.model.Round;
import com.github.tmd.gamelog.domain.scoreboard.model.Round.RoundId;
import com.github.tmd.gamelog.domain.scoreboard.model.RoundScore;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard;
import com.github.tmd.gamelog.domain.scoreboard.model.Scoreboard.ScoreboardId;
import com.github.tmd.gamelog.domain.scoreboard.model.ScoreboardStatus;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScoreboardMapperTest {

  @Autowired
  ScoreboardMapper scoreboardMapper;

  @Test
  void toPersistence() {
    // Given
    var round0 = new Round(new RoundId(UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd")), 0);
    var round1 = new Round(new RoundId(UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83")), 1);

    var player0 = new Player(new PlayerId(UUID.fromString("20d7264c-954d-4964-afbb-59bdbfe60b2d")), "player0");
    var player1 = new Player(new PlayerId(UUID.fromString("dfd4527c-8b5c-4adb-b7ec-6971218feb29")), "player1");

    var scores = Map.of(
        player0, Set.of(new RoundScore(round0), new RoundScore(round1)),
        player1, Set.of(new RoundScore(round0), new RoundScore(round1))
    );

    var scoreboard = new Scoreboard(
        new ScoreboardId(UUID.fromString("de275ef4-85bc-4b24-a3d1-ee75dcffd96d")),
        new Game(new GameId(UUID.fromString("230611f2-31c7-46a3-91dc-3b43f2959c0f")),
            Set.of(round0, round1)),
        scores,
        ScoreboardStatus.LOCKED
    );

    // When
    var jpa = scoreboardMapper.toPersistence(scoreboard);

    // Then
    assertThat(jpa).isNotNull();
    assertThat(jpa.getScoreboardId()).isEqualByComparingTo(UUID.fromString("de275ef4-85bc-4b24-a3d1-ee75dcffd96d"));
    assertThat(jpa.getGameJpa().getGameId()).isEqualByComparingTo(UUID.fromString("230611f2-31c7-46a3-91dc-3b43f2959c0f"));
    assertThat(jpa.getGameJpa().getRounds()).hasSize(2);
    assertThat(jpa.getGameJpa().getRounds()).extracting("roundId", "roundNumber")
        .containsExactlyInAnyOrder(
            tuple(UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0),
            tuple(UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1));
    assertThat(jpa.getScores()).hasSize(4);
    assertThat(jpa.getScores()).extracting("player.playerId", "round.roundId", "round.roundNumber")
        .containsExactlyInAnyOrder(
            tuple(UUID.fromString("20d7264c-954d-4964-afbb-59bdbfe60b2d"), UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0),
            tuple(UUID.fromString("dfd4527c-8b5c-4adb-b7ec-6971218feb29"), UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0),
            tuple(UUID.fromString("20d7264c-954d-4964-afbb-59bdbfe60b2d"), UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1),
            tuple(UUID.fromString("dfd4527c-8b5c-4adb-b7ec-6971218feb29"), UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1)
        );
    assertThat(jpa.getStatus()).isEqualTo(ScoreboardStatusJpa.LOCKED);
  }

  @Test
  void toDomain() {
    // Given
    var player0 = new PlayerJpa(UUID.fromString("20d7264c-954d-4964-afbb-59bdbfe60b2d"), "player0");
    var player1 = new PlayerJpa(UUID.fromString("dfd4527c-8b5c-4adb-b7ec-6971218feb29"), "player1");

    var round0 = new RoundJpa(UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0);
    var round1 = new RoundJpa(UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1);

    var gameJpa = new GameJpa(UUID.fromString("230611f2-31c7-46a3-91dc-3b43f2959c0f"), GameStatusJpa.CREATED, Set.of(round0, round1));

    var scores = Set.of(
        new RoundScoreJpa(player0, round0),
        new RoundScoreJpa(player1, round0),
        new RoundScoreJpa(player0, round1),
        new RoundScoreJpa(player1, round1)
    );

    var scoreboardJpa = new ScoreboardJpa(UUID.fromString("de275ef4-85bc-4b24-a3d1-ee75dcffd96d"), gameJpa, scores, ScoreboardStatusJpa.LOCKED);

    // When
    var scoreboard = this.scoreboardMapper.toDomain(scoreboardJpa);

    // Then
    assertThat(scoreboard).isNotNull();
    assertThat(scoreboard.getScoreboardId().id()).isEqualTo(UUID.fromString("de275ef4-85bc-4b24-a3d1-ee75dcffd96d"));
    assertThat(scoreboard.getGame().getId().gameId()).isEqualTo(UUID.fromString("230611f2-31c7-46a3-91dc-3b43f2959c0f"));
    assertThat(scoreboard.getGame().getGameStatus()).isEqualTo(GameStatus.CREATED);
    assertThat(scoreboard.getGame().getRounds()).hasSize(2);
    assertThat(scoreboard.getGame().getRounds()).extracting("roundId.roundId", "roundNumber")
        .containsExactlyInAnyOrder(
            tuple(UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0),
            tuple(UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1));
    assertThat(scoreboard.getRoundScores()).hasSize(2);

    // Well I don't really know how to test the scoreboard properly with AssertJ. Going to do a
    // procedural approach
    var expectedPlayer0 = new Player(new PlayerId(UUID.fromString("20d7264c-954d-4964-afbb-59bdbfe60b2d")), "player0");
    var expectedPlayer1 = new Player(new PlayerId(UUID.fromString("dfd4527c-8b5c-4adb-b7ec-6971218feb29")), "player1");
    var scorePlayer0 = scoreboard.getRoundScores().get(expectedPlayer0);
    var scorePlayer1 = scoreboard.getRoundScores().get(expectedPlayer1);
    assertThat(scorePlayer0).hasSize(2);
    assertThat(scorePlayer0).extracting("round.roundId.roundId", "round.roundNumber")
        .containsExactlyInAnyOrder(
            tuple(UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0),
            tuple(UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1));
    assertThat(scorePlayer1).hasSize(2).extracting("round.roundId.roundId", "round.roundNumber")
        .containsExactlyInAnyOrder(
            tuple(UUID.fromString("63e9b601-1cc9-4759-878a-ff32335bc7bd"), 0),
            tuple(UUID.fromString("8f5edc35-8791-4c38-950c-07dab2763c83"), 1));
    assertThat(scoreboard.getStatus()).isEqualTo(ScoreboardStatus.LOCKED);
  }
}