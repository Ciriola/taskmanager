package com.iptq.taskmanager.exception;

public class ProcessNotFoundException extends RuntimeException {

    public ProcessNotFoundException(String message) {
        super(message);
    }
}
