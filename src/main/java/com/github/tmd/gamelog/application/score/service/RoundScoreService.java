package com.github.tmd.gamelog.application.score.service;

import com.github.tmd.gamelog.adapter.jpa.score.RoundScoreJpa;
import com.github.tmd.gamelog.application.PlayerService;
import com.github.tmd.gamelog.domain.Player;
import com.github.tmd.gamelog.domain.score.vo.AggregatedRoundScore;
import com.github.tmd.gamelog.application.score.RoundScoreAggregator;
import com.github.tmd.gamelog.domain.score.repository.AggregatedRoundScoreRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoundScoreService {
  private final RoundScoreAggregator roundScoreAggregator;
  private final AggregatedRoundScoreRepository roundScoreRepository;
  private final PlayerService playerService;


  public RoundScoreService(
      RoundScoreAggregator roundScoreAggregator,
      AggregatedRoundScoreRepository roundScoreRepository,
      PlayerService playerService) {
    this.roundScoreAggregator = roundScoreAggregator;
    this.roundScoreRepository = roundScoreRepository;
    this.playerService = playerService;
  }

  public Map<Player, List<AggregatedRoundScore>> getAllOrderedAggregatedScoresInGame(UUID gameId) {
    try {
      var result = this.roundScoreRepository.findAllOrderedRoundScoresInGame(gameId)
          .entrySet()
          .stream()
          .collect(Collectors.toMap(gs -> resolvePlayerId(gs.getKey()), gs -> gs.getValue()));
      return result;
    } catch (RuntimeException e) {
      log.error("Could not aggregate rounds for game with ID %s".formatted(gameId), e);
      throw e;
    }
  }

  public Map<Player, AggregatedRoundScore> getAggregatedRoundScoresForRound(UUID roundId) {
    var foundScores = this.roundScoreRepository.getAggregatedRoundScoresForRound(roundId)
        .entrySet()
        .stream()
        .collect(Collectors.toMap(rs -> resolvePlayerId(rs.getKey()), rs -> rs.getValue()));

    // If somehow there are no scores, we could just create them - however this is not useful
    // since that most likely happens if we're in the middle of a synchronous call and therefore
    // would store incorrect scores...
    if(foundScores == null) {
      throw new RuntimeException("Scores for round with ID %s not found".formatted(roundId));
    }

    return foundScores;
  }

  public Map<Player, AggregatedRoundScore> accumulateAndSaveRoundScoresForRound(UUID roundId) {
    var roundScoreBuilder = RoundScoreJpa.builder()
        .roundId(roundId);

    Map<UUID, AggregatedRoundScore> scores =
        this.roundScoreAggregator.aggregateRoundScoresForRound(roundId);

    this.roundScoreRepository.saveAggregatedRoundScoresForRound(roundId, scores);

    return scores
        .entrySet()
        .stream()
        .collect(Collectors.toMap(rs -> resolvePlayerId(rs.getKey()), rs -> rs.getValue()));
  }

  // Helper method to create a fallback if we don't have a player for the ID
  private Player resolvePlayerId(UUID playerId) {
    return playerService.findPlayerById(playerId)
        .orElse(new Player(playerId));
  }
}
