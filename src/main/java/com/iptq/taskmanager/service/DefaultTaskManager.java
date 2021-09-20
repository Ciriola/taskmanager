package com.iptq.taskmanager.service;

import com.iptq.taskmanager.domain.Process;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class DefaultTaskManager extends TaskManager {

    public final int capacity;
    public Queue<Process> runningProcesses;

    public DefaultTaskManager(int capacity) {
        super(capacity);
        this.capacity = capacity;
        this.runningProcesses = new ArrayBlockingQueue<>(capacity);
    }
}
