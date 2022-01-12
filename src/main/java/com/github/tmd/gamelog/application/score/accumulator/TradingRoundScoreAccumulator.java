package com.github.tmd.gamelog.application.score.accumulator;

import com.github.tmd.gamelog.application.history.TradingHistoryService;
import com.github.tmd.gamelog.application.score.core.AbstractCategorizedRoundScoreAccumulator;
import com.github.tmd.gamelog.application.score.TradingRoundScore;
import com.github.tmd.gamelog.domain.score.core.ScoreCategory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TradingRoundScoreAccumulator extends
    AbstractCategorizedRoundScoreAccumulator<TradingRoundScore> {
  private final TradingHistoryService tradingHistoryService;

  public TradingRoundScoreAccumulator(
      TradingHistoryService tradingHistoryService) {
    this.tradingHistoryService = tradingHistoryService;
  }

  @Override
  public Map<UUID, TradingRoundScore> accumulateRoundScores(UUID roundId) {
    var playerBalances = tradingHistoryService.getPlayerBalancesInRound(roundId);
    var playerTrades = tradingHistoryService.getNumberOfTradesInRound(roundId);

    // Ugly but readable hack, since I don't know how to combine them in a fancy manner
    var allPlayerIds = new HashSet<>(playerBalances.keySet());
    allPlayerIds.addAll(playerTrades.keySet());

    var tradingRoundScores = new HashMap<UUID, TradingRoundScore>();
    for(UUID id : allPlayerIds) {
      var balance = playerBalances.getOrDefault(id, 0);
      var numOfTrades = playerTrades.getOrDefault(id, 0);
      tradingRoundScores.put(id, new TradingRoundScore(balance, numOfTrades));
    }

    return tradingRoundScores;
  }
}