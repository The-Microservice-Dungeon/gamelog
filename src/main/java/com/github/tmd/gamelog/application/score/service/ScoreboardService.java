package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.application.GameService;
import com.github.tmd.gamelog.domain.Game;
import com.github.tmd.gamelog.domain.Game.GameId;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.PlayerRepository;
import com.github.tmd.gamelog.domain.score.entity.Scoreboard;
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

  public Optional<Scoreboard> getScoreboardByGameId(GameId id) {
    return this.gameService.findGameById(id)
        .map(this::buildFromGame);
  }

  public Optional<Scoreboard> getScoreboardOfActiveGame() {
    return this.gameService.findActiveGame()
        .map(this::buildFromGame);
  }

  private Scoreboard buildFromGame(Game game) {
    var gameScores = gameScoreService.getScoresInGame(game.getId().id());
    var roundScores = roundScoreService.getAllOrderedAggregatedScoresInGame(game.getId().id());

    var scoreboard = Scoreboard.builder()
        .gameScores(gameScores)
        .game(game)
        .roundScores(roundScores)
        .build();

    return scoreboard;
  }
}
