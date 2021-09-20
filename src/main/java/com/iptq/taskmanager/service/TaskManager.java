package com.iptq.taskmanager.service;

import com.iptq.taskmanager.domain.Priority;
import com.iptq.taskmanager.domain.Process;
import com.iptq.taskmanager.exception.ProcessNotFoundException;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public abstract class TaskManager {

    protected int capacity;
    protected Queue<Process> runningProcesses;

    TaskManager(int capacity) {
        this.capacity = capacity;
        this.runningProcesses = new ArrayBlockingQueue<>(capacity);
    }

    protected void addProcess(Process process) {
        if (runningProcesses.offer(process)) {
            process.setCreation(Instant.now());
        }
    }

    protected List<Process> list() {
        return runningProcesses.stream()
                .sorted(Comparator.comparing(Process::getCreation))
                .collect(Collectors.toList());
    }

    protected void kill(int PID) {
        Process process = runningProcesses.stream()
                .filter(p -> p.getPID() == PID)
                .findFirst()
                .orElseThrow(() -> new ProcessNotFoundException("Process with pid : " + PID + " not found"));

        process.kill();
        runningProcesses.remove(process);
    }

    protected void killGroup(Priority priority) {
        Set<Process> toKill = runningProcesses.stream()
                .filter(p -> p.getPriority() == priority)
                .collect(Collectors.toSet());

        toKill.forEach(p -> {
            p.kill();
            runningProcesses.remove(p);
        });
    }

    protected void killAll() {
        runningProcesses.forEach(Process::kill);
        runningProcesses.clear();
    }
}
