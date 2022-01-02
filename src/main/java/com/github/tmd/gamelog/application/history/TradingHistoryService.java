package com.github.tmd.gamelog.application.history;

import com.github.tmd.gamelog.adapter.jpa.history.trading.PlayerBalanceHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.PlayerBalanceHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.rest_client.client.TradingRestClient;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingHistoryService {
  private final TradingHistoryJpaRepository tradingHistoryJpaRepository;
  private final PlayerBalanceHistoryJpaRepository playerBalanceHistoryJpaRepository;
  private final TradingRestClient tradingRestClient;

  @Autowired
  public TradingHistoryService(
      TradingHistoryJpaRepository tradingHistoryJpaRepository,
      PlayerBalanceHistoryJpaRepository playerBalanceHistoryJpaRepository,
      TradingRestClient tradingRestClient) {
    this.tradingHistoryJpaRepository = tradingHistoryJpaRepository;
    this.playerBalanceHistoryJpaRepository = playerBalanceHistoryJpaRepository;
    this.tradingRestClient = tradingRestClient;
  }

  public void insertTradingHistory(UUID transactionId, Integer moneyChangeAmount, Temporal timestamp) {
    this.tradingHistoryJpaRepository.save(new TradingHistoryJpa(transactionId, moneyChangeAmount, Instant.from(timestamp)));
  }

  /**
   *
   * @throws RuntimeException if a error occurs in the synchronous call
   */
  @Transactional
  public void insertBalanceHistory(UUID roundId) {
    try {
      // We cannot use the roundId to retrieve the player balances for a given round. However,
      // we do can ASSUME that the results belong to the given round.
      var result = this.tradingRestClient.getAllPlayersAccountBalances()
          .stream().map(r -> new PlayerBalanceHistoryJpa(r.playerId(), r.balance(), roundId))
          .collect(Collectors.toSet());
      this.playerBalanceHistoryJpaRepository.saveAll(result);
    } catch (RuntimeException e) {
      log.error("Could not load balances", e);
      throw e;
    }
  }
}
