package com.github.tmd.gamelog.adapter.rest.server.scoreboard;


import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedScore;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ScoreboardControllerTest {

  @MockBean
  ScoreboardService scoreboardService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void getScoreboardOfActiveGame() {
    var game = new Game(new GameId(UUID.randomUUID()));
    var scoreboard = buildDummyScoreboard(game);
    when(scoreboardService.getScoreboardOfActiveGame()).thenReturn(Optional.of(scoreboard));

    given()
        .mockMvc(mockMvc)
        .accept("application/json")
    .when()
        .get("/scoreboard")
    .then()
        .log().ifValidationFails()
        .statusCode(200)
        .body("scoreboard", hasSize(2))
        .body("scoreboard[0].totalScore", notNullValue())
        .body("scoreboard[0].player.id", equalTo("bc234084-c980-41eb-8ff3-c94c46d81a46"))
        .body("scoreboard[0].player.name", equalTo("Leonie"))
        .body("scoreboard[1].totalScore", notNullValue())
        .body("scoreboard[1].player.id", equalTo("e67180fb-3582-4772-9c0b-411a1b1b8306"))
        .body("scoreboard[1].player.name", equalTo("Karl-Peter"));
  }

  @Test
  void getScoreboardOfGame() {
    var gameId = new GameId(UUID.fromString("759aa05d-76c6-450a-ab21-ee688a73fccb"));
    var game = new Game(gameId);
    var scoreboard = buildDummyScoreboard(game);
    when(scoreboardService.getScoreboardByGameId(eq(gameId))).thenReturn(Optional.of(scoreboard));

    given()
        .mockMvc(mockMvc)
        .accept("application/json")
    .when()
        .get("/scoreboard/{game-id}", gameId.id())
    .then()
        .log().all()
        .statusCode(200)
        .body("scoreboard", hasSize(2))
        .body("scoreboard[0].totalScore", notNullValue())
        .body("scoreboard[0].player.id", equalTo("bc234084-c980-41eb-8ff3-c94c46d81a46"))
        .body("scoreboard[0].player.name", equalTo("Leonie"))
        .body("scoreboard[1].totalScore", notNullValue())
        .body("scoreboard[1].player.id", equalTo("e67180fb-3582-4772-9c0b-411a1b1b8306"))
        .body("scoreboard[1].player.name", equalTo("Karl-Peter"));
  }

  private Scoreboard buildDummyScoreboard(Game game) {
    var player1 = new Player(UUID.fromString("e67180fb-3582-4772-9c0b-411a1b1b8306"), "Karl-Peter");
    var player2 = new Player(UUID.fromString("bc234084-c980-41eb-8ff3-c94c46d81a46"), "Leonie");

    var player1_round1 = AggregatedScore.builder()
        .fightingScore(1.1)
        .miningScore(2.2)
        .robotScore(3.3)
        .movementScore(4.4)
        .build();

    var player1_round2 = AggregatedScore.builder()
        .fightingScore(10.1)
        .miningScore(20.2)
        .robotScore(30.3)
        .movementScore(40.4)
        .build();

    var player2_round1 = AggregatedScore.builder()
        .fightingScore(11.1)
        .miningScore(22.2)
        .robotScore(33.3)
        .movementScore(44.4)
        .build();

    var player2_round2 = AggregatedScore.builder()
        .fightingScore(21.1)
        .miningScore(22.2)
        .robotScore(23.3)
        .movementScore(24.4)
        .build();

    var player1_game = AggregatedScore.builder()
        .fightingScore(11.1)
        .miningScore(22.2)
        .robotScore(33.3)
        .movementScore(44.4)
        .build();

    var player2_game = AggregatedScore.builder()
        .fightingScore(32.2)
        .miningScore(44.4)
        .robotScore(56.6)
        .movementScore(68.8)
        .build();

    return Scoreboard.builder()
        .game(game)
        .roundScores(Map.of(
            player1, List.of(player1_round1, player1_round2),
            player2, List.of(player2_round1, player2_round2)
        ))
        .gameScores(Map.of(
            player1, player1_game,
            player2, player2_game
        ))
        .build();
  }
}