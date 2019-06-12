package com.beejeem.core.job;

import java.util.UUID;

public interface CommandResult {

    public enum CommandResultStatus {
        OK,
        ERROR
    }

    /**
     * Get command exit status
     * @return status
     */
    public CommandResultStatus getResultStatus();

    /**
     * Get the id of the command
     * @return id of the command
     */
    public UUID getCommandID();


}
