package com.github.tmd.gamelog.handler;

import com.github.tmd.gamelog.model.TestMessage;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConsumer {

  private final MeterRegistry meterRegistry;
  private final Counter testCounter;

  @Autowired
  public TestConsumer(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    this.testCounter = this.meterRegistry.counter("test");
  }

  @KafkaListener(topics = "test")
  public void logTest(ConsumerRecord<String, TestMessage> record, @Payload TestMessage str) {
    var key = record.key();
    var payload = str;
    var headers = StreamSupport.stream(record.headers().spliterator(), false)
        .map(header -> new String(header.value())).collect(Collectors.toUnmodifiableList());
    log.info("Received player event - key {}: Headers [{}] | Payload: {} | Record: {}", key,
        headers, payload, record);
    testCounter.increment();
  }
}
