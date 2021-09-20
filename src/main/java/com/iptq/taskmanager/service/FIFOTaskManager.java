package com.iptq.taskmanager.service;

import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 * The Circular FIFO queue, is implemented making use of the
 * CircularFifoQueue belonging to apache commons library.
 * Like this, we avoid to write unnecessary code, and we can
 * make it part of the TaskManager abstraction,
 * being the CircularFifoQueue an subclass of the java.util.Queue;
 */
public class FIFOTaskManager extends TaskManager {

    public final int capacity;

    public FIFOTaskManager(int capacity) {
        super(capacity);
        this.capacity = capacity;
        runningProcesses = new CircularFifoQueue<>(capacity);
    }
}
