package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.gameEventManagement.application.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final Logger LOG = LoggerFactory.getLogger(KafkaListeners.class);

    @Autowired
    EventPublisher eventPublisher;

    @KafkaListener(id = "1", topics = "event", groupId = "reflectoring-user-mc", containerFactory = "kafkaJsonListenerContainerFactory")
    void listenerWithMessageConverter(Event event) {
        LOG.info("New Kafka Event [{}]", event);
        eventPublisher.publish(event);
    }
}
