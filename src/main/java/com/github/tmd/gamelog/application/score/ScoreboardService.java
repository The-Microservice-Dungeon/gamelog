package com.github.tmd.gamelog.application.score;

import com.github.tmd.gamelog.application.score.dto.ScoreboardDto;
import com.github.tmd.gamelog.application.score.dto.ScoreboardEntryDto;
import com.github.tmd.gamelog.application.score.dto.ScoreboardPlayerEntryDto;
import com.github.tmd.gamelog.domain.PlayerRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoreboardService {
  private final GameScoreService gameScoreService;
  private final PlayerRepository playerRepository;

  public ScoreboardService(
      GameScoreService gameScoreService,
      PlayerRepository playerRepository) {
    this.gameScoreService = gameScoreService;
    this.playerRepository = playerRepository;
  }

  public Optional<ScoreboardDto> getScoreboardForGame(UUID gameId) {
    try {
      var scoreboardEntries = new ArrayList<ScoreboardEntryDto>();
      gameScoreService.getScoresInGame(gameId).entrySet()
              .stream().sorted(Comparator.comparing(o -> o.getValue().score()))
              .forEachOrdered(e -> {
                scoreboardEntries.add(new ScoreboardEntryDto(
                    new ScoreboardPlayerEntryDto(e.getKey(), "__PLACEHOLDER__"),
                    e.getValue().score()
                ));
              });
      return Optional.of(new ScoreboardDto(scoreboardEntries));
    } catch (RuntimeException e) {
      log.error("Could not determine a scoreboard for game %s".formatted(gameId));
      return Optional.empty();
    }
  }
}
