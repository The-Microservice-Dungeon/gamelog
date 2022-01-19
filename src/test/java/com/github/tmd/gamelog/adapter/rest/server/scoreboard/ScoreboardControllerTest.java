package com.github.tmd.gamelog.adapter.rest.server.scoreboard;


import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import com.github.tmd.gamelog.domain.score.vo.AggregatedGameScore;
import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.hamcrest.Matchers.*;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
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
        .body("scoreboard[0].score", equalTo(202.0f))
        .body("scoreboard[0].player.id", equalTo("bc234084-c980-41eb-8ff3-c94c46d81a46"))
        .body("scoreboard[0].player.name", equalTo("Leonie"))
        .body("scoreboard[1].score", equalTo(112.0f))
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
        .body("scoreboard[0].score", equalTo(202.0f))
        .body("scoreboard[0].player.id", equalTo("bc234084-c980-41eb-8ff3-c94c46d81a46"))
        .body("scoreboard[0].player.name", equalTo("Leonie"))
        .body("scoreboard[1].score", equalTo(112.0f))
        .body("scoreboard[1].player.id", equalTo("e67180fb-3582-4772-9c0b-411a1b1b8306"))
        .body("scoreboard[1].player.name", equalTo("Karl-Peter"));
  }

  private Scoreboard buildDummyScoreboard(Game game) {
    var player1 = new Player(UUID.fromString("e67180fb-3582-4772-9c0b-411a1b1b8306"), "Karl-Peter");
    var player2 = new Player(UUID.fromString("bc234084-c980-41eb-8ff3-c94c46d81a46"), "Leonie");

    var player1_round1 = AggregatedRoundScore.builder()
        .fightingScore(1.1)
        .miningScore(2.2)
        .robotScore(3.3)
        .movementScore(4.4)
        .build();

    var player1_round2 = AggregatedRoundScore.builder()
        .fightingScore(10.1)
        .miningScore(20.2)
        .robotScore(30.3)
        .movementScore(40.4)
        .build();

    var player2_round1 = AggregatedRoundScore.builder()
        .fightingScore(11.1)
        .miningScore(22.2)
        .robotScore(33.3)
        .movementScore(44.4)
        .build();

    var player2_round2 = AggregatedRoundScore.builder()
        .fightingScore(21.1)
        .miningScore(22.2)
        .robotScore(23.3)
        .movementScore(24.4)
        .build();

    var player1_game = new AggregatedGameScore(112.0);
    var player2_game = new AggregatedGameScore(202.0);

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