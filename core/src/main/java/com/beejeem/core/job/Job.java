package com.beejeem.core.job;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.parser.domain.Program;

import java.util.UUID;

/**
 * Interface for the job class.
 */
public interface Job {

    /**
     * Job states
     */
    public enum JobState {
        READY,
        RUN,
        STOP,
        ERROR,
        FINISH
    }

    /**
     * Get the name of the job
     * @return name of the job
     */
    public String getName();

    /**
     * Get the ID
     * @return the ID
     */
    public UUID getID();

    /**
     * Get job program
     * @return program of the job
     */
    public Program getProgram();

    /**
     * Start the job
     */
    public void start();

    /**
     * Stop the job
     */
    public void stop();

    /**
     * Restart the job
     */
    public void restart();

    /**
     * Get the current status of the job
     * @return current status of the job
     */
    public UUID getState();

    /**
     * Process the result of a command
     * @param result command result
     */
    public void processCommandResult(CommandResult result);

}
