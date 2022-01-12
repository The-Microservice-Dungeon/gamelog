package com.github.tmd.gamelog.adapter.api;

import com.github.tmd.gamelog.application.score.ScoreboardService;
import com.github.tmd.gamelog.application.score.dto.ScoreboardDto;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ScoreboardController {
  private final ScoreboardService scoreboardService;

  public ScoreboardController(
      ScoreboardService scoreboardService) {
    this.scoreboardService = scoreboardService;
  }

  @GetMapping("scoreboard/{game-id}")
  public ResponseEntity<ScoreboardDto> getScoreboardOfGame(@PathVariable("game-id") UUID gameId) {
    return scoreboardService.getScoreboardForGame(gameId)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
