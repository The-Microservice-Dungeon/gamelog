package com.github.tmd.gamelog.eventManagement.application.eventTypes;

import javax.persistence.Entity;

@Entity
public class RoundEndEvent extends Event {

    private int round;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

}
