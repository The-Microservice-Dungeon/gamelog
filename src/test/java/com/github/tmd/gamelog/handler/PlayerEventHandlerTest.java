package com.github.tmd.gamelog.handler;

import com.github.tmd.gamelog.model.Player;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(topics = "players", bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class PlayerEventHandlerTest {
  static {
    System.setProperty(EmbeddedKafkaBroker.BROKER_LIST_PROPERTY, "spring.kafka.bootstrap-servers");
  }

  @Autowired
  KafkaTemplate<String, Object> template;

  @Test
  void testPublishPlayer() throws Exception {
    var player = new Player(UUID.randomUUID(), "Xavier Tester");
    this.template.send("players", player.id().toString(), player);
    Thread.sleep(1000L); // Blocking the thread... Well...
  }
}
