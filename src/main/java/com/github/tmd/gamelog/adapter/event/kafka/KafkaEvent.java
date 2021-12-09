package com.github.tmd.gamelog.adapter.event.kafka;


import com.github.tmd.gamelog.adapter.event.eventTypes.AbstractEvent;

import javax.persistence.Column;

public class Event extends AbstractEvent {

    @Column()
    private String payload;
}
