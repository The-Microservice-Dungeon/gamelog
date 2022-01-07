package com.github.tmd.gamelog.adapter.event.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration")
@SpringBootTest(properties = { "spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer" })
@EnabledIfEnvironmentVariable(disabledReason = "Should run in CI", named = "CI", matches = "true")
class TradingEventListenersIntegrationTest {

  @MockBean
  TradingHistoryService tradingHistoryService;

  @Autowired
  KafkaTemplate<String, Object> template;

  @Test
  void tradingEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        {
          "success": true,
          "moneyChangedBy": -500,
          "message": "",
          "data": {}
        }
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("trades")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("trades")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(tradingHistoryService, after(5000).only()).insertTradingHistory(eq(transactionId), eq(-500), any());
  }
}