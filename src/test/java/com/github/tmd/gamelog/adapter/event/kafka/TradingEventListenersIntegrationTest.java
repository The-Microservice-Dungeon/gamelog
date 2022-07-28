package com.github.tmd.gamelog.adapter.event.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatus;
import com.github.tmd.gamelog.application.GameLifecycleHook;
import com.github.tmd.gamelog.application.history.TradingHistoryService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration")
@SpringBootTest(properties = { "spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer" })
@DirtiesContext
@EnabledIfEnvironmentVariable(disabledReason = "Should run in CI", named = "CI", matches = "true")
class TradingEventListenersIntegrationTest {

  @MockBean
  @Qualifier("testLifeCycleHook")
  GameLifecycleHook testLifeCycleHook;

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
    verify(testLifeCycleHook, after(5000).only()).onTrade(any(), eq(transactionId), any());
  }

 /* @Test
  void currentItemPricesChangedEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        [{"price":210,"name":"NUKE"},{"price":80,"name":"SELF_DESTRUCTION"},{"price":90,"name":"REPAIR_SWARM"},{"price":80,"name":"WORMHOLE"},{"price":60,"name":"LONG_RANGE_BOMBARDEMENT"},{"price":40,"name":"ROCKET"}]
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("current-item-prices")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("current-item-prices")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(testLifeCycleHook, after(5000).only()).onCurrentItemPricesAnnouncement(any(), any());
  }
*/
  @Test
  void currentResourcePricesChangedEvent() {
    // Given
    var transactionId = UUID.randomUUID();
    var payload = """
        [{"price":50,"name":"gold"},{"price":30,"name":"gem"},{"price":60,"name":"platin"},{"price":15,"name":"iron"},{"price":5,"name":"coal"}]
        """;
    var key = UUID.randomUUID().toString();

    var producerRecord = KafkaProducerRecordBuilder.<String, Object>builder()
        .topic("current-resource-prices")
        .payload(payload)
        .key(key)
        .eventId(UUID.randomUUID().toString())
        .transactionId(transactionId.toString())
        .type("current-resource-prices")
        .build().toProducerRecord();

    // When
    template.send(producerRecord);

    // Then
    verify(testLifeCycleHook, after(5000).only()).onCurrentResourcePricesAnnouncement(any(), any());
  }
}