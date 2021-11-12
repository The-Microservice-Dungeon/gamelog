package com.github.tmd.gamelog.eventManagement.application.listener;

import com.github.tmd.gamelog.eventManagement.application.eventRepositorys.MovementEventRepository;
import com.github.tmd.gamelog.eventManagement.application.eventTypes.MovementEvent;
import com.github.tmd.gamelog.eventManagement.application.eventTypes.RoundEndEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RoundEndEventListener {

    private final Logger LOG = LoggerFactory.getLogger(MovementEventListener.class);

    @Autowired
    private MovementEventRepository movementEventRepository;

    @EventListener
    public void handleRoundEndEvent(RoundEndEvent event) {
        LOG.info("RoundEndEventListener: [{}]", event);

        List<MovementEvent> movements = movementEventRepository.findByRound(1);

        LOG.info("Movements: {}", movements.legth);

        //sum Movement events
    }

}
