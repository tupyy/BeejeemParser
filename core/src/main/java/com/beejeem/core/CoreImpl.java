package com.beejeem.core;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.interpreter.CommandInterpreter;
import com.beejeem.core.command.interpreter.DefaultCommandInterpreter;
import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.executor.CommandExecutor;
import com.beejeem.core.executor.CommandResultManager;
import com.beejeem.core.executor.CommandResultManagerImpl;
import com.beejeem.core.executor.DefaultCommandExecutor;
import com.beejeem.core.job.DefaultJob;
import com.beejeem.core.job.Job;
import com.beejeem.core.job.actions.DefaultJobCommandAction;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.parser.DefaultParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class CoreImpl implements Core{

    private static Core instance = null;

    private CommandExecutor<CommandExecutable> commandExecutor;
    private CommandResultManager<CommandResult> commandResultManager = new CommandResultManagerImpl();
    private List<Job> jobs = new ArrayList<>();

    private CoreImpl() {
        this.commandExecutor = new DefaultCommandExecutor(commandResultManager);
    }

    public static Core getInstance() {
        if (instance == null) {
            instance = new CoreImpl();
        }
        return instance;
    }

    @Override
    public UUID createJob(String name, String programCode) throws ParseCancellationException {
        Program program = new DefaultParser().parse(programCode);
        CommandInterpreter<CommandExecutable> interpreter = new DefaultCommandInterpreter();
        Job job = new DefaultJob(name, program, new DefaultJobCommandAction(interpreter, this.commandExecutor));
        jobs.add(job);
        return job.getID();
    }

    @Override
    public void deleteJob(UUID jobID) {

    }

    @Override
    public Job getJob(UUID jobID) {
        return null;
    }
}
