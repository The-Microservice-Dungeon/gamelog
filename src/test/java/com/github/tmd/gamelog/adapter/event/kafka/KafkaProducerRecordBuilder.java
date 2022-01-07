package com.github.tmd.gamelog.adapter.event.kafka;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import org.apache.kafka.clients.producer.ProducerRecord;

  @Builder
  public class KafkaProducerRecordBuilder<K, V> {
    @NonNull
    private String topic;
    @NonNull
    private K key;
    @NonNull
    private V payload;
    @Builder.Default
    private String eventId = UUID.randomUUID().toString();
    @Builder.Default
    private String transactionId = UUID.randomUUID().toString();
    @Builder.Default
    private Integer version = 1;
    @Builder.Default
    private String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
    @Builder.Default
    private String type = "";

    public ProducerRecord<K, V> toProducerRecord() {
      var producerRecord = new ProducerRecord<K, V>(topic, key, payload);
      producerRecord.headers().add("eventId", eventId.getBytes());
      producerRecord.headers().add("transactionId", transactionId.getBytes());
      producerRecord.headers().add("version",  new byte[] { version.byteValue() });
      producerRecord.headers().add("timestamp", timestamp.getBytes());
      producerRecord.headers().add("type", type.getBytes());
      return producerRecord;
    }
  }