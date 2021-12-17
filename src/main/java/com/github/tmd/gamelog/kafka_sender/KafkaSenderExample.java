package com.github.tmd.gamelog.kafka_sender;

import com.github.tmd.gamelog.adapter.event.gameEvent.game.GameStatusEvent;
import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderExample {

    private final Logger LOG = LoggerFactory.getLogger(KafkaSenderExample.class);

    private KafkaTemplate<String, KafkaEvent> eventKafkaTemplate;
    private final KafkaTemplate<String, String> stringBasedKafkaTemplate;

    @Autowired
    KafkaSenderExample(KafkaTemplate<String, KafkaEvent> eventKafkaTemplate,
        KafkaTemplate<String, String> stringBasedKafkaTemplate) {
        this.eventKafkaTemplate = eventKafkaTemplate;
        this.stringBasedKafkaTemplate = stringBasedKafkaTemplate;
    }


    void sendEvent(String topic, KafkaEvent event) {
        LOG.info("Sending Json Serializer : {}", event);
        LOG.info("--------------------------------");

        var record = new ProducerRecord<String, KafkaEvent>(topic, event);
        if(!event.getType().isBlank()) {
            record.headers().add("type", event.getType().getBytes());
        }

        eventKafkaTemplate.send(record);
    }

    void sendGameStatusEvent(String topic, String type, GameStatusEvent event) {
        // Send the event using the Message type, when using the Producer Record, the Spring Boot
        // config will not or just partially work
        Message<GameStatusEvent> message = MessageBuilder.withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader("type", type)
            .build();

        stringBasedKafkaTemplate.send(message);
    }

}
