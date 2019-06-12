package com.beejeem.core.job;

public interface CommandResult {

    public enum CommandResultStatus {
        OK,
        ERROR
    }

    public CommandResultStatus getResultStatus();


}
