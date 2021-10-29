package com.github.tmd.gamelog.handler;

import com.github.tmd.gamelog.model.Player;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlayerConsumer {

  @KafkaListener(topics = "players")
  public void logPlayer(ConsumerRecord<String, Player> record, @Payload Player player) {
    var key = record.key();
    var payload = player;
    var headers = StreamSupport.stream(record.headers().spliterator(), false)
            .map(header -> new String(header.value())).collect(Collectors.toUnmodifiableList());
    log.info("Received player event - key {}: Headers [{}] | Payload: {} | Record: {}", key,
        headers, payload, record);
  }
}
