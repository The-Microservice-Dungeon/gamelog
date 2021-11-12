package com.github.tmd.gamelog.gameEvent;

import com.github.tmd.gamelog.Event;

public class MovementEvent extends Event {

    private int robotId;

    private int start;

    private int end;

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

    @Override
    public String toString() {
        return "MovementEvent{" +
                "robotId=" + robotId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
