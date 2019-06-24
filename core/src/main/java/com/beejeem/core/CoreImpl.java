package com.beejeem.core;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.executor.CommandExecutor;
import com.beejeem.core.executor.CommandExecutorImpl;
import com.beejeem.core.executor.CommandResultManagerImpl;
import com.beejeem.core.job.Job;
import com.beejeem.core.job.JobFactory;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.parser.DefaultParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public final class CoreImpl implements Core{

    private static Core instance = null;

    private CommandExecutor<CommandExecutable> commandExecutor;

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
    public CommandExecutor getCommandExecutor() {
        if (this.commandExecutor == null) {
            commandExecutor = new CommandExecutorImpl(new CommandResultManagerImpl());
        }

        return commandExecutor;
    }
}
