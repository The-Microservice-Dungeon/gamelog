package com.github.tmd.gamelog.eventManagement.application;

import com.github.tmd.gamelog.eventManagement.application.eventTypes.Event;
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

    public void publish(final Event event) {
        LOG.info("Publishing Event [{}]", event);
        applicationEventPublisher.publishEvent(event);
    }

}
