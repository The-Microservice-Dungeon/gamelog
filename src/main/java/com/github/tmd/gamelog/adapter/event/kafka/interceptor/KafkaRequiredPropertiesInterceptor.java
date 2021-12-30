package com.github.tmd.gamelog.adapter.event.kafka.interceptor;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaDungeonHeader;
import java.nio.ByteBuffer;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;

/**
 * This Interceptor will check every consumed event whether the required headers defined in <a
 * href="https://the-microservice-dungeon.github.io/decisionlog/decisions/event-structure.html">Event
 * Structure</a> are present. If not in every case a warning will be logged.
 * <p>
 * In addition, the headers are checked whether they are in a valid format.
 * <p>
 * It might be conceivable to recover from these errors and set the headers manual. As of now the
 * following
 */
@Slf4j
public class KafkaRequiredPropertiesInterceptor implements ConsumerInterceptor<String, String> {

  private final Set<String> requiredHeaders = Set.of(
      KafkaDungeonHeader.KEY_EVENT_ID,
      KafkaDungeonHeader.KEY_TRANSACTION_ID,
      KafkaDungeonHeader.KEY_VERSION,
      KafkaDungeonHeader.KEY_TIMESTAMP,
      KafkaDungeonHeader.KEY_TYPE);
  private final Map<String, Function<Header, Boolean>> validators = Map.of(
      KafkaDungeonHeader.KEY_EVENT_ID, header -> validateUUID(header),
      KafkaDungeonHeader.KEY_TRANSACTION_ID, header -> validateUUID(header),
      KafkaDungeonHeader.KEY_VERSION, header -> validateVersion(header),
      KafkaDungeonHeader.KEY_TIMESTAMP, header -> validateTimestamp(header),
      KafkaDungeonHeader.KEY_TYPE, header -> validateType(header)
  );

  @Override
  public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
    var iter = records.iterator();
    iter.forEachRemaining(record -> {
      for (var headerName : requiredHeaders) {
        var header = record.headers().lastHeader(headerName);
        var topic = record.topic();
        var partition = record.partition();
        if (header != null) {
          var validator = validators.get(header);
          if (validator != null) {
            var isValid = validator.apply(header);
            if (!isValid) {
              var str = new String(header.value());
              log.warn("Header {} is not in a valid format: {} (Topic: {}, Partition: {})", headerName, str, topic, partition);
            }
          }
        } else {
          log.warn("No {} header present on consumed event (Topic: {}, Partition: {})", headerName, topic, partition);
        }
      }
    }
    );
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

  private final boolean validateUUID(Header header) {
    var str = new String(header.value());
    try {
      var id = UUID.fromString(str);
      return id != null;
    } catch (IllegalArgumentException e) {
      log.debug("Header Value %s is not a valid UUID".formatted(str), e);
    }

    return false;
  }

  private final boolean validateVersion(Header header) {
    var version = ByteBuffer.wrap(header.value()).getInt();
    return version > 0;
  }

  private final boolean validateTimestamp(Header header) {
    var str = new String(header.value());
    DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    try {
      var parsed = formatter.parse(str);
      return parsed != null;
    } catch (DateTimeParseException e) {
      log.debug("Header Value %s is not a valid Timestamp".formatted(str), e);
    }

    return false;
  }

  private final boolean validateType(Header header) {
    var str = new String(header.value());
    return str != null && !str.isBlank();
  }
}
