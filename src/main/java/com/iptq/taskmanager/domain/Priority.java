package com.iptq.taskmanager.domain;

public enum Priority {
    LOW(1), MEDIUM(2), HIGH(3);

    Priority(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
