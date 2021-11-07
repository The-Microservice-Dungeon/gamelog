package com.github.tmd.gamelog.config;

import com.github.tmd.gamelog.model.TestMessage;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTaskConfig {

  private final KafkaTemplate<String, TestMessage> template;

  public ScheduledTaskConfig(
      KafkaTemplate<String, TestMessage> template) {
    this.template = template;
  }

  @Scheduled(fixedRate = 1000)
  public void publishTestMessage() {
    this.template.send("test", UUID.randomUUID().toString(), new TestMessage("Lorem Ipsum"));
  }
}
