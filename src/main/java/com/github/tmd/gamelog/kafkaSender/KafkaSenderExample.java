package com.github.tmd.gamelog.kafkaSender;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderExample {

    private final Logger LOG = LoggerFactory.getLogger(KafkaSenderExample.class);

    private KafkaTemplate<String, KafkaEvent> eventKafkaTemplate;

    @Autowired
    KafkaSenderExample(KafkaTemplate<String, KafkaEvent> eventKafkaTemplate) {
        this.eventKafkaTemplate = eventKafkaTemplate;
    }


    void sendEvent(KafkaEvent event) {
        LOG.info("Sending Json Serializer : {}", event);
        LOG.info("--------------------------------");

        eventKafkaTemplate.send("event", event);
    }

}
