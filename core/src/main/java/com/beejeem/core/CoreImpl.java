package com.beejeem.core;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.executor.CommandExecutor;
import com.beejeem.core.job.Job;
import com.beejeem.core.job.JobFactory;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.parser.DefaultParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public final class CoreImpl implements Core {

    private static Core instance = null;

    @Autowired
    private CommandExecutor<CommandExecutable> commandExecutor;

    private List<Job> jobs = new ArrayList<>();

    public CoreImpl() {
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
        return commandExecutor;
    }
}
