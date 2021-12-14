package com.github.tmd.gamelog.adapter.event.gameEvent;

import com.github.tmd.gamelog.adapter.event.kafka.KafkaEvent;
import com.github.tmd.gamelog.domain.RoundScore;

public interface EventInterface {
    public static String getEventName()
    {
        throw new RuntimeException("This method needs to be implemented");
    }

    public void execute(RoundScore gameContext);

    public static EventInterface fromKafkaEvent(KafkaEvent kafkaEvent)
    {
        throw new RuntimeException("This method needs to be implemented");
    }
}
