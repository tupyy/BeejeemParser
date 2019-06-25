package com.beejeem.core.job;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.interpreter.CommandInterpreter;
import com.beejeem.core.command.interpreter.CommandInterpreterImpl;
import com.beejeem.core.executor.CommandExecutor;
import com.beejeem.core.job.actions.DefaultJobCommandAction;
import com.beejeem.parser.domain.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Job factory class.
 */
@Component
public class JobFactory {

    private static CommandExecutor commandExecutor;

    @Autowired
    public void setCommandExecutor(CommandExecutor tmpCommandExecutor) {
        commandExecutor = tmpCommandExecutor;
    }

    /**
     * Return a default job.
     * @param name job name
     * @param program program object
     * @return {@link DefaultJob}
     */
    public static Job createDefaultJob(String name, Program program) {
        CommandInterpreter<CommandExecutable> commandInterpreter = new CommandInterpreterImpl();
        return new DefaultJob(name, program, new DefaultJobCommandAction(commandInterpreter, commandExecutor));
    }
}
