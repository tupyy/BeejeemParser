package com.beejeem.core;

import com.beejeem.core.job.Job;
import com.beejeem.core.job.JobFactory;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.parser.DefaultParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class CoreImpl implements Core{

    private static Core instance = null;

    /**
     * Create an executor for local jobs.
     */
    private ThreadPoolExecutor localExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    /**
     * Create a remote executor. This solution is used because there are problems with ssh session if
     * more than 2 jobs are executed in parallel.
     * TODO Find a better solution for executing remote commands
     */
    private ThreadPoolExecutor remoteExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    private List<Job> jobs = new ArrayList<>();

    private CoreImpl() {
    }

    public static Core getInstance() {
        if (instance == null) {
            instance = new CoreImpl();
        }
        return instance;
    }

    @Override
    public UUID createJob(String name, String programCode) {
        Program program = new DefaultParser().parse(programCode);
        Job job = JobFactory.createDefaultJob(name, program);
        jobs.add(job);
        return job.getID();
    }

    @Override
    public void deleteJob(UUID jobID) {
        throw  new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Job getJob(UUID jobID) {
        return jobs.stream()
                   .filter(j -> j.getID().equals(jobID))
                   .findAny()
                   .orElse(null);
    }

    @Override
    public ExecutorService getLocalExecutor() {
        return localExecutor;
    }

    @Override
    public ExecutorService getRemoteExecutor() {
        return remoteExecutor;
    }
}
