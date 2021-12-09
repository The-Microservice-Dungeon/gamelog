package com.github.tmd.gamelog.adapter.event.kafka;

import com.github.tmd.gamelog.adapter.event.EventPublisher;
import com.github.tmd.gamelog.adapter.event.gameEvent.MovementEvent;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListeners {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEventListeners.class);
    private final EventPublisher eventPublisher;

    public KafkaEventListeners(EventPublisher eventPublisher)
    {
        this.eventPublisher = eventPublisher;
    }

    @KafkaListener(
        id = "1",
        topics = "event",
        groupId = "reflectoring-user-mc",
        containerFactory = "kafkaJsonListenerContainerFactory"
    )
    public void listenMovementTopic(ConsumerRecord<?, ?> kafkaEvent) {
        System.out.println(kafkaEvent.headers());
        System.out.println(kafkaEvent.value());
    }
}
