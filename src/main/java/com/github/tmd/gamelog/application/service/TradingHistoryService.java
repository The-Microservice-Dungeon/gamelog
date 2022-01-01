package com.github.tmd.gamelog.application.service;

import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpaRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradingHistoryService {
  private final TradingHistoryJpaRepository tradingHistoryJpaRepository;

  @Autowired
  public TradingHistoryService(
      TradingHistoryJpaRepository tradingHistoryJpaRepository) {
    this.tradingHistoryJpaRepository = tradingHistoryJpaRepository;
  }

  public void insertTradingHistory(UUID transactionId, Integer moneyChangeAmount) {
    this.tradingHistoryJpaRepository.save(new TradingHistoryJpa(transactionId, moneyChangeAmount));
  }
}
