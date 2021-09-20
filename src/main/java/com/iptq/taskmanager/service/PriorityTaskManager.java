package com.iptq.taskmanager.service;

import com.iptq.taskmanager.domain.Process;

import java.time.Instant;
import java.util.PriorityQueue;

public class PriorityTaskManager extends TaskManager {

    public final int capacity;

    public PriorityTaskManager(int capacity) {
        super(capacity);
        this.capacity = capacity;
        runningProcesses = new PriorityQueue<>(capacity);
    }

    @Override
    protected void addProcess(Process process) {
        if(runningProcesses.size() == capacity) {
            runningProcesses.poll();
        }
        if (runningProcesses.offer(process)) {
            process.setCreation(Instant.now());
        }
    }
}
