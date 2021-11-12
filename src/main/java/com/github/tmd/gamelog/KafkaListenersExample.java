package com.github.tmd.gamelog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenersExample {

    private final Logger LOG = LoggerFactory.getLogger(KafkaListenersExample.class);

    @Autowired
    NormalizerService normalizerService;

    @Autowired
    EventPublisher eventPublisher;

    @KafkaListener(id = "1", topics = "event", groupId = "reflectoring-user-mc", containerFactory = "kafkaJsonListenerContainerFactory")
    void listenerWithMessageConverter(Event event) {
        LOG.info("Event [{}]", event);
        Event e = normalizerService.normalize(event);
        LOG.info("Normalized Event [{}]", e);
        eventPublisher.publish(e);
    }




}
