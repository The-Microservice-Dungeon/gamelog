package com.github.tmd.gamelog;

public class Event {

    private String createdAt;
    private String eventType;

    private String payload;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "createdAt=" + createdAt +
                ", eventType='" + eventType + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
