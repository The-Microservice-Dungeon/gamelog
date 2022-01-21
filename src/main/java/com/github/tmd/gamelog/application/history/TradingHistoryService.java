package com.github.tmd.gamelog.application.history;

import com.github.tmd.gamelog.adapter.jpa.history.trading.PlayerBalanceHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.PlayerBalanceHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.rest.client.TradingRestClient;
import com.github.tmd.gamelog.adapter.rest.client.response.PlayerBalanceRoundEntryDto;
import feign.FeignException;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
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
  public void insertBalanceHistory(UUID roundId, Integer roundNumber) {
    try {
      List<PlayerBalanceRoundEntryDto> result;
      try {
        result = this.tradingRestClient.getAllPlayerBalancesOfRound(roundNumber);
      } catch (FeignException e) {
        // TODO: We can't assume any balances for the round if the call fails. A proper solution would
        //  be to schedule the call for a later point and updating the database entry. However this
        //  is not feasible with our current architecture and would require way more effort than
        //  we can invest atm. Therefore we're defaulting to an empty collection and hope for the
        //  best that the other services are resilient.
        log.error("Couldn't retrieve Balances of round {}. Defaulting to an empty collection...", roundNumber, e);
        result = Collections.emptyList();
      }
      var mappedToPersistence = result.stream()
          .map(r -> new PlayerBalanceHistoryJpa(r.playerId(), r.balance(), roundId))
          .collect(Collectors.toSet());
      this.playerBalanceHistoryJpaRepository.saveAll(mappedToPersistence);
    } catch (RuntimeException e) {
      log.error("Could not load balances", e);
      throw e;
    }
  }

  public Map<UUID, Integer> getPlayerBalancesInRound(UUID roundId) {
    return this.playerBalanceHistoryJpaRepository.findAllBalancesInRound(roundId)
        .stream().collect(Collectors.toMap(
            b -> b.getPlayerId(),
            b -> b.getBalance()
        ));
  }

  public Integer getPlayerBalanceInRound(UUID playerId, UUID roundId) {
    return this.getPlayerBalancesInRound(roundId).get(playerId);
  }

  public Map<UUID, Integer> getNumberOfTradesInRound(UUID roundId) {
    return this.tradingHistoryJpaRepository.findNumberOfTradesInRound(roundId)
        .stream().collect(Collectors.toMap(t -> t.getPlayerId(), t -> t.getNumberOfTrades()));
  }

  public Integer getNumberOfTradesForPlayerInRound(UUID playerId, UUID roundId) {
    return this.tradingHistoryJpaRepository.findTradingHistoryForPlayerInRound(playerId, roundId)
        .size();
  }
}
