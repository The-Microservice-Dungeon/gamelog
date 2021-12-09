package com.github.tmd.gamelog.adapter.event;

import com.github.tmd.gamelog.adapter.event.gameEvent.EventInterface;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    @org.springframework.context.event.EventListener
    public void consume(EventInterface gameEvent)
    {

    }
}
