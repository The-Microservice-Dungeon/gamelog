package com.github.tmd.gamelog.domain.game;

import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.game.Round.RoundId;
import com.github.tmd.gamelog.domain.game.vo.RoundScore;
import com.github.tmd.gamelog.domain.player.PlayerService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// TODO: Refactor the service
@Service
public class GameService {
  private final GameRepository gameRepository;
  private final PlayerService playerService;

  @Autowired
  public GameService(GameRepository gameRepository,
      PlayerService playerService) {
    this.gameRepository = gameRepository;
    this.playerService = playerService;
  }

  public Game createNewGame(UUID gameId) {
    var game = new Game(new GameId(gameId), new HashSet<>());
    return this.gameRepository.save(game);
  }

  public void startGame(UUID gameId) {
    var game = getOrThrow(gameId);

    game.setGameStatus(GameStatus.STARTED);
    this.gameRepository.save(game);
  }

  public void endGame(UUID gameId) {
    var game = getOrThrow(gameId);

    game.setGameStatus(GameStatus.ENDED);
    this.gameRepository.save(game);
  }

  public void addRound(UUID gameId, UUID roundId, Integer roundNumber) {
    var game = getOrThrow(gameId);
    var round = new Round(new RoundId(roundId), roundNumber, new HashMap<>());
    game.addRound(round);

    this.gameRepository.save(game);
  }

  public void addScore(UUID gameId, UUID roundId, UUID playerId, Integer testScore) {
    var player = playerService.getPlayerById(playerId);
    var game = getOrThrow(gameId);
    var round = game.getRounds().stream().filter(r -> r.getRoundId().roundId().equals(roundId)).findFirst().orElseThrow();

    var score = new RoundScore(testScore);

    round.addScore(player, score);

    this.gameRepository.save(game);
  }

  private Game getOrThrow(UUID gameId) {
    var optGame = gameRepository.findGameById(new GameId(gameId));
    if(optGame.isEmpty()) throw new RuntimeException("Game with id %s not found".formatted(gameId));
    return optGame.get();
  }
}
