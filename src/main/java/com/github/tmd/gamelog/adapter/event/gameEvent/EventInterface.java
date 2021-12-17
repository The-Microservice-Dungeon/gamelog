package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import com.github.tmd.gamelog.domain.RoundScore;

/**
 * Defines how an event is represented in our service
 */
public interface EventInterface {

    /**
     * Defines a unique discriminator for the event
     * @return discriminator
     */
    public static String getEventName()
    {
        throw new RuntimeException("This method needs to be implemented");
    }

    /**
     * Handles the event information on how it affects the round score
     * @param gameContext
     */
    public void execute(RoundScore gameContext);

    /**
     * Parse a kafka consumed event into the event type
     * @param kafkaEvent event from kafka
     * @return instance of event
     */
    public static EventInterface fromKafkaEvent(KafkaEvent kafkaEvent)
    {
        throw new RuntimeException("This method needs to be implemented");
    }
}
