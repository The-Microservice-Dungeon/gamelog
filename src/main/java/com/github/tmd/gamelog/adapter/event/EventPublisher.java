package com.github.tmd.gamelog.adapter.event;

import com.github.tmd.gamelog.adapter.event.gameEvent.EventInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    @Autowired
    private final ApplicationEventPublisher applicationEventPublisher;

    EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private static final Logger LOG = LoggerFactory.getLogger(EventPublisher.class);

    public void publish(final EventInterface event)
    {
        LOG.info("Publish enrich event for event [{}]", event);
        applicationEventPublisher.publishEvent(event);
    }

}
