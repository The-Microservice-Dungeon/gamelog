package com.github.tmd.gamelog.adapter.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListeners {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEventListeners.class);
    private final KafkaEventHandler kafkaEventHandler;

    public KafkaEventListeners(KafkaEventHandler kafkaEventHandler)
    {
        this.kafkaEventHandler = kafkaEventHandler;
    }

    @KafkaListener(
        id = "1",
        topics = "event",
        groupId = "reflectoring-user",
        containerFactory = "kafkaJsonListenerContainerFactory"
    )
    public void listenMovementTopic(ConsumerRecord<?, ?> kafkaEvent) {
        System.out.println(kafkaEvent.headers());
        System.out.println(kafkaEvent.value());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            KafkaEvent event = objectMapper.readValue((String)kafkaEvent.value(), KafkaEvent.class);
            kafkaEventHandler.handleEvent(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
