package com.github.tmd.gamelog.adapter.rest.server.scoreboard;

import com.github.tmd.gamelog.adapter.rest.server.scoreboard.mapper.ScoreboardMapper;
import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import com.github.tmd.gamelog.adapter.rest.server.scoreboard.dto.ScoreboardJsonDto;
import com.github.tmd.gamelog.domain.Game.GameId;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST Controller for the Scoreboard
 */
@RestController
public class ScoreboardController {
  private final ScoreboardService scoreboardService;
  private final ScoreboardMapper scoreboardMapper;

  public ScoreboardController(
      ScoreboardService scoreboardService,
      ScoreboardMapper scoreboardMapper) {
    this.scoreboardService = scoreboardService;
    this.scoreboardMapper = scoreboardMapper;
  }

  /**
   * Get the scoreboard for the current running game
   * @return ResponseEntity with Scoreboard as payload
   * @throws ResponseStatusException if no scoreboard was found for the game
   */
  @GetMapping(value = "scoreboard", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ScoreboardJsonDto> getScoreboardOfActiveGame() {
    return scoreboardService.getScoreboardOfActiveGame()
        .map(this.scoreboardMapper::toJson)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  /**
   * Get the Scoreboard for a Game
   * @param gameId Game ID
   * @return ResponseEntity with Scoreboard as payload
   * @throws ResponseStatusException if no scoreboard was found for the game
   */
  @GetMapping(value = "scoreboard/{game-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ScoreboardJsonDto> getScoreboardOfGame(@PathVariable("game-id") UUID gameId) {
    return scoreboardService.getScoreboardByGameId(new GameId(gameId))
        .map(this.scoreboardMapper::toJson)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
