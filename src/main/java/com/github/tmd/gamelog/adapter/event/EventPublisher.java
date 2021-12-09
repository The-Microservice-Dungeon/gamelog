package com.github.tmd.gamelog.gameEventManagement.application;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private static final Logger LOG = LoggerFactory.getLogger(EventPublisher.class);

    public void publish(final KafkaEvent event) {
        // enrich event
        EnrichEvent enrichEvent = new EnrichEvent(event);
        LOG.info("Publish enrich event for event [{}]", event);
        applicationEventPublisher.publishEvent(enrichEvent);

        // validate event
        LOG.info("Publish validation event for event [{}]", event);
        // applicationEventPublisher.publishEvent(event);
    }

}
