package com.github.tmd.gamelog.eventManagement.application.eventTypes;

import javax.persistence.Entity;

@Entity
public class MovementEvent extends Event {

    private int robotId;

    private int start;

    private int end;

    private int round;

    public int getRobotId() {
        return robotId;
    }

    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    @Override
    public String toString() {
        return "MovementEvent{" +
                "robotId=" + robotId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
