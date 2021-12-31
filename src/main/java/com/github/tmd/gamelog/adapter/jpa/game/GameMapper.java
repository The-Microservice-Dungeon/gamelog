package com.github.tmd.gamelog.adapter.jpa.game;

import com.github.tmd.gamelog.adapter.jpa.player.PlayerMapper;
import com.github.tmd.gamelog.domain.game.Game;
import com.github.tmd.gamelog.domain.game.Game.GameId;
import com.github.tmd.gamelog.domain.game.Round;
import com.github.tmd.gamelog.domain.game.Round.RoundId;
import com.github.tmd.gamelog.domain.game.vo.RoundScore;
import com.github.tmd.gamelog.domain.player.Player;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {
  private final PlayerMapper playerMapper;

  public GameMapper(PlayerMapper playerMapper) {
    this.playerMapper = playerMapper;
  }

  public Game toDomain(GameJpa gameJpa) {
    var rounds = gameJpa.getRounds().stream().map(this::toDomain).collect(Collectors.toSet());
    var game = new Game(new GameId(gameJpa.getGameId()), rounds);
    game.setGameStatus(gameJpa.getStatus());
    return game;
  }

  public GameJpa toPersistence(Game game) {
    var gameJpa = new GameJpa();
    gameJpa.setGameId(game.getId().gameId());
    gameJpa.setStatus(game.getGameStatus());
    gameJpa.setRounds(game.getRounds().stream().map(this::toPersistence).collect(Collectors.toSet()));
    return gameJpa;
  }

  private RoundJpa toPersistence(Round round) {
    var jpa = new RoundJpa();
    jpa.setRoundId(round.getRoundId().roundId());
    jpa.setRoundNumber(round.getRoundNumber());

    var scores = toPersistence(round.getScores());
    jpa.setScores(scores);

    return jpa;
  }

  private Round toDomain(RoundJpa roundJpa) {
    var scores = toDomain(roundJpa.getScores());
    var round = new Round(new RoundId(roundJpa.getRoundId()), roundJpa.getRoundNumber(), scores);

    return round;
  }

  private Set<RoundScoreJpa> toPersistence(Map<Player, RoundScore> scores) {
    return scores.entrySet()
        .stream()
        .map(entry -> toPersistence(entry.getKey(), entry.getValue()))
        .collect(Collectors.toSet());
  }

  private RoundScoreJpa toPersistence(Player player, RoundScore score) {
    var jpa = new RoundScoreJpa();

    jpa.setPlayer(playerMapper.toPersistence(player));

    // Scores
    jpa.setTestScore(score.testScore());

    return jpa;
  }

  private Map<Player, RoundScore> toDomain(Set<RoundScoreJpa> roundScoreJpa) {
    return roundScoreJpa.stream()
        .collect(Collectors.toMap(p -> playerMapper.toDomain(p.getPlayer()), p -> this.toDomain(p)));
  }

  private RoundScore toDomain(RoundScoreJpa jpa) {
    var score = new RoundScore(jpa.getTestScore());
    return score;
  }
}
