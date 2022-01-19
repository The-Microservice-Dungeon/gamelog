package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.application.score.dto.ScoreboardDto;
import com.github.tmd.gamelog.application.score.dto.ScoreboardEntryDto;
import com.github.tmd.gamelog.application.score.dto.ScoreboardPlayerEntryDto;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerRepository;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoreboardService {
  private final GameScoreService gameScoreService;
  private final PlayerRepository playerRepository;
  private final RoundScoreService roundScoreService;
  private final GameService gameService;

  public ScoreboardService(
      GameScoreService gameScoreService,
      PlayerRepository playerRepository,
      RoundScoreService roundScoreService,
      GameService gameService) {
    this.gameScoreService = gameScoreService;
    this.playerRepository = playerRepository;
    this.roundScoreService = roundScoreService;
    this.gameService = gameService;
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
      log.error("Could not determine a scoreboard for game %s".formatted(gameId), e);
      return Optional.empty();
    }
  }

  public Optional<Scoreboard> getScoreboardByGameId(GameId id) {
    return this.gameService.findGameById(id)
        .map(this::buildFromGame);
  }

  public Optional<Scoreboard> getScoreboardOfActiveGame() {
    return this.gameService.findActiveGame()
        .map(this::buildFromGame);
  }

  private Scoreboard buildFromGame(Game game) {
    var gameScores = gameScoreService.getScoresInGame(game.getId().id())
        .entrySet()
        .stream()
        .collect(Collectors.toMap(gs -> resolvePlayerId(gs.getKey()), gs -> gs.getValue()));
    var roundScores = roundScoreService.getAllOrderedAggregatedScoresInGame(game.getId().id())
        .entrySet()
        .stream()
        .collect(Collectors.toMap(rs -> resolvePlayerId(rs.getKey()), rs -> rs.getValue()));

    var scoreboard = Scoreboard.builder()
        .gameScores(gameScores)
        .game(game)
        .roundScores(roundScores)
        .build();

    return scoreboard;
  }

  // Helper method to create a fallback if we don't have a player for the ID
  private Player resolvePlayerId(UUID playerId) {
    return playerRepository.findById(playerId)
        .orElse(new Player(playerId));
  }
}
