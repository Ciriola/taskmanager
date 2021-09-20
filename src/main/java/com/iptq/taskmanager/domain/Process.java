package com.iptq.taskmanager.domain;

import java.time.Instant;
import java.util.Objects;

public class Process implements Comparable<Process> {

    private final int PID;
    private final Priority priority;
    private boolean active;
    private Instant creation;

    public Process(int pid, Priority priority) {
        PID = pid;
        this.active = true;
        this.priority = priority;
    }

    public void kill() {
        this.active = false;
    }

    public int getPID() {
        return PID;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setCreation(Instant creation) {
        this.creation = creation;
    }

    public Instant getCreation() {
        return creation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return PID == process.PID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(PID);
    }

    @Override
    public String toString() {
        return "Process{" +
                "PID=" + PID +
                ", priority=" + priority +
                ", active=" + active +
                ", creation=" + creation +
                '}';
    }

    @Override
    public int compareTo(Process o) {
        return this.priority.getValue() - o.getPriority().getValue();
    }
}
