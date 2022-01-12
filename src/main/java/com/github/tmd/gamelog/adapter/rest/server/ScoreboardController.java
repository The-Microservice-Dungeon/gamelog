package com.github.tmd.gamelog.adapter.rest.server;

import com.github.tmd.gamelog.application.score.service.ScoreboardService;
import com.github.tmd.gamelog.application.score.dto.ScoreboardDto;
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

  public ScoreboardController(
      ScoreboardService scoreboardService) {
    this.scoreboardService = scoreboardService;
  }

  /**
   * Get the Scoreboard for a Game
   * @param gameId Game ID
   * @return ResponseEntity with Scoreboard as payload
   * @throws ResponseStatusException if no scoreboard was found for the game
   */
  @GetMapping(value = "scoreboard/{game-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ScoreboardDto> getScoreboardOfGame(@PathVariable("game-id") UUID gameId) {
    return scoreboardService.getScoreboardForGame(gameId)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
