package com.github.tmd.gamelog;

import com.github.tmd.gamelog.gameEvent.MovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MovementEventListener {

    private final Logger LOG = LoggerFactory.getLogger(MovementEventListener.class);

    @EventListener
    public void handleMovementEvent(MovementEvent event) {
        LOG.info("EventListener: [{}]", event);
    }

}
