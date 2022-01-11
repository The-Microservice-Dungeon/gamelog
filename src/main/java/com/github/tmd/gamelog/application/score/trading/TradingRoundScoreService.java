package com.github.tmd.gamelog.application.score.trading;

import com.github.tmd.gamelog.application.history.TradingHistoryService;
import com.github.tmd.gamelog.application.score.core.AbstractRoundScoreService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TradingRoundScoreService extends AbstractRoundScoreService<TradingRoundScore> {
  private final TradingHistoryService tradingHistoryService;

  public TradingRoundScoreService(
      TradingHistoryService tradingHistoryService) {
    this.tradingHistoryService = tradingHistoryService;
  }

  public TradingRoundScore getRoundScoreForPlayerInRound(UUID playerId, UUID roundId) {
    var balance = tradingHistoryService.getPlayerBalanceInRound(playerId, roundId);
    var numOfTrades = tradingHistoryService.getNumberOfTradesForPlayerInRound(playerId, roundId);

    return new TradingRoundScore(balance, numOfTrades);
  }
}
