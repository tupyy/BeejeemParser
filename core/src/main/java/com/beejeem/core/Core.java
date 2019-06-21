package com.beejeem.core;

import com.beejeem.core.job.Job;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * Core interface.
 * It provides a simple interface to CRUD job operations.
 */
public interface Core {

    /**
     * Create a job from a code
     * @param name of the job
     * @param programCode String containing the code of the program
     * @throws ParseCancellationException if the code cannot be parsed
     * @return the ID of the job
     */
    public UUID createJob(String name, String programCode) throws ParseCancellationException;

    /**
     * Delete job.
     * @param jobID id of the job
     */
    public void deleteJob(UUID jobID);

    /**
     * Get job
     * @param jobID id of the job
     * @return Job
     */
    public Job getJob(UUID jobID);

    /**
     * Return the executor used to run local commands
     * @return ThreadPoolExecutor
     */
    public ExecutorService getLocalExecutor();

    /**
     * Return the executor used to run remote commands
     * @return ThreadPoolExecutor
     */
    public ExecutorService getRemoteExecutor();

}
