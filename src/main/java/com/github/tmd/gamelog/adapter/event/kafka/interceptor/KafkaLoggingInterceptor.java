package com.github.tmd.gamelog.adapter.event.kafka.interceptor;

import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;

@Slf4j
public class KafkaLoggingInterceptor implements ConsumerInterceptor<Object, Object> {

  @Override
  public ConsumerRecords<Object, Object> onConsume(ConsumerRecords<Object, Object> records) {
    var iter = records.iterator();
    iter.forEachRemaining(record -> {
      var topic = record.topic();
      var partition = record.partition();
      var offset = record.offset();
      var key = Objects.toString(record.key());
      var value = Objects.toString(record.value());
      var headers = headersToString(record.headers());

      log.trace("""
          Received Message in consumer
          Topic: %s
          Partition: %s
          Offset: %s
          ---
          Headers:
          %s
          ---
          Key: %s
          Value: %s
          """.formatted(topic, partition, offset, headers, key, value));
    });
    return records;
  }

  @Override
  public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {

  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> configs) {

  }

  public final String headersToString(Headers headers) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(headers.iterator(), Spliterator.ORDERED) ,false)
        .map(header -> "Key: %s, Value: %s\n".formatted(header.key(), new String(header.value())))
        .collect(Collectors.joining());
  }
}
