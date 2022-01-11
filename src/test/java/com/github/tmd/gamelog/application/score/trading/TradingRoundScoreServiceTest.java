package com.github.tmd.gamelog.application.score.trading;

import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.game.CommandHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.trading.PlayerBalanceHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.PlayerBalanceHistoryJpaRepository;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpa;
import com.github.tmd.gamelog.adapter.jpa.history.trading.TradingHistoryJpaRepository;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class TradingRoundScoreServiceTest {

  @Autowired
  TradingHistoryJpaRepository tradingHistoryJpaRepository;

  @Autowired
  PlayerBalanceHistoryJpaRepository playerBalanceHistoryJpaRepository;

  @Autowired
  CommandHistoryJpaRepository commandHistoryJpaRepository;

  @Autowired
  TradingRoundScoreAccumulator tradingRoundScoreService;

  private UUID player1 = UUID.fromString("4d4711ea-0a29-454d-9897-62280408b0f2");
  private UUID player2 = UUID.fromString("847b1721-e603-46a3-8663-c895b0694a2e");

  private UUID round1 = UUID.fromString("05444aea-7aa5-4d52-bb6d-74aaea84cb9e");
  private UUID round2 = UUID.fromString("aec36e14-b5ec-431b-8ec7-44e6455e76d5");

  private UUID gameId = UUID.randomUUID();

  @BeforeEach
  void setUp() {
    var transactionId1 = UUID.fromString("03aaad1c-50cb-4cad-bdde-d4fcf77ba4f7");
    var transactionId2 = UUID.fromString("b6d22978-3371-499c-96ce-2c1519c7777a");
    var transactionId3 = UUID.fromString("d9e6064b-8ad4-413d-af26-d8b60ddea64f");
    var transactionId4 = UUID.fromString("48afe0b3-d7fb-46e1-a236-67f12de1f850");
    var transactionId5 = UUID.fromString("15e5c311-efe9-4294-a374-1011789466c6");

    tradingHistoryJpaRepository.saveAll(Set.of(
        new TradingHistoryJpa(transactionId1, -500, Instant.now()),
        new TradingHistoryJpa(transactionId2, 250, Instant.now()),
        new TradingHistoryJpa(transactionId3, 50, Instant.now()),
        new TradingHistoryJpa(transactionId4, 21, Instant.now()),
        new TradingHistoryJpa(transactionId5, -12345, Instant.now())
    ));
    playerBalanceHistoryJpaRepository.saveAll(Set.of(
        new PlayerBalanceHistoryJpa(player1, 2050, round1),
        new PlayerBalanceHistoryJpa(player1, 1234, round2),
        new PlayerBalanceHistoryJpa(player2, 2050, round1),
        new PlayerBalanceHistoryJpa(player2, 2048, round2)
    ));
    commandHistoryJpaRepository.saveAll(Set.of(
        new CommandHistoryJpa(transactionId1, gameId, round1, player1),
        new CommandHistoryJpa(transactionId2, gameId, round1, player2),
        new CommandHistoryJpa(transactionId3, gameId, round2, player1),
        new CommandHistoryJpa(transactionId4, gameId, round2, player1),
        new CommandHistoryJpa(transactionId5, gameId, round2, player2),
        new CommandHistoryJpa(UUID.randomUUID(), gameId, UUID.randomUUID(), player1),
        new CommandHistoryJpa(UUID.randomUUID(), gameId, UUID.randomUUID(), player2)
    ));
  }

  @Test
  void shouldGetTradingRoundScoreForRound1() {
    // When
    var player1Score = this.tradingRoundScoreService.getRoundScoreForPlayerInRound(player1, round1);
    var player2Score = this.tradingRoundScoreService.getRoundScoreForPlayerInRound(player2, round1);

    // Then
    assertThat(player1Score.balance()).isEqualTo(2050);
    assertThat(player2Score.balance()).isEqualTo(2050);

    var b = this.commandHistoryJpaRepository.findAll();
    var a = this.tradingHistoryJpaRepository.findAll();

    assertThat(player1Score.numOfTrades()).isEqualTo(1);
    assertThat(player2Score.numOfTrades()).isEqualTo(1);
  }

  @Test
  void shouldGetTradingRoundScoreForRound2() {
    // When
    var player1Score = this.tradingRoundScoreService.getRoundScoreForPlayerInRound(player1, round2);
    var player2Score = this.tradingRoundScoreService.getRoundScoreForPlayerInRound(player2, round2);

    // Then
    assertThat(player1Score.balance()).isEqualTo(1234);
    assertThat(player2Score.balance()).isEqualTo(2048);

    assertThat(player1Score.numOfTrades()).isEqualTo(2);
    assertThat(player2Score.numOfTrades()).isEqualTo(1);
  }
}