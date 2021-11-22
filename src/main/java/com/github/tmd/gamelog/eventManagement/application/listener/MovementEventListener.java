package com.github.tmd.gamelog.eventManagement.application.listener;

import com.github.tmd.gamelog.eventManagement.application.eventRepositorys.MovementEventRepository;
import com.github.tmd.gamelog.eventManagement.application.eventTypes.MovementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MovementEventListener {

    private final Logger LOG = LoggerFactory.getLogger(MovementEventListener.class);

    private final MovementEventRepository movementEventRepository;

    public MovementEventListener(MovementEventRepository movementEventRepository) {
        this.movementEventRepository = movementEventRepository;
    }

    @EventListener
    public void handleMovementEvent(MovementEvent event) {
        LOG.info("MovementEventListener: [{}]", event);
        this.movementEventRepository.save(event);
        LOG.info("MovementEventListener SAVED: [{}]", event);
    }

}
