package com.beejeem.core.result;

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

    /**
     * Get id of the job
     * @return id the job
     */
    public UUID getJobID();


}
