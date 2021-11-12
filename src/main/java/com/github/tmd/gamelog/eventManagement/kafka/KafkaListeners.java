package com.github.tmd.gamelog.eventManagement.kafka;

import com.github.tmd.gamelog.eventManagement.application.eventTypes.Event;
import com.github.tmd.gamelog.eventManagement.application.EventNormalizerService;
import com.github.tmd.gamelog.eventManagement.application.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final Logger LOG = LoggerFactory.getLogger(KafkaListeners.class);

    @Autowired
    EventNormalizerService eventNormalizerService;

    @Autowired
    EventPublisher eventPublisher;

    @KafkaListener(id = "1", topics = "event", groupId = "reflectoring-user-mc", containerFactory = "kafkaJsonListenerContainerFactory")
    void listenerWithMessageConverter(Event event) {
        LOG.info("Event [{}]", event);
        Event e = eventNormalizerService.normalize(event);
        LOG.info("Normalized Event [{}]", e);
        eventPublisher.publish(e);
    }




}
