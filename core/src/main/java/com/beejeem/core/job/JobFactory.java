package com.beejeem.core.job;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.interpreter.CommandInterpreter;
import com.beejeem.core.command.interpreter.DefaultCommandInterpreter;
import com.beejeem.core.executor.CommandExecutor;
import com.beejeem.core.executor.CommandResultManagerImpl;
import com.beejeem.core.executor.DefaultCommandExecutor;
import com.beejeem.core.job.actions.DefaultJobCommandAction;
import com.beejeem.parser.domain.Program;

/**
 * Job factory class.
 */
public class JobFactory {

    /**
     * Return a default job.
     * @param name job name
     * @param program program object
     * @return {@link DefaultJob}
     */
    public static Job createDefaultJob(String name, Program program) {
        CommandInterpreter<CommandExecutable> commandInterpreter = new DefaultCommandInterpreter();
        CommandExecutor<CommandExecutable> commandResultCommandExecutor = new DefaultCommandExecutor(new CommandResultManagerImpl());
        return new DefaultJob(name, program, new DefaultJobCommandAction(commandInterpreter, commandResultCommandExecutor));
    }
}
