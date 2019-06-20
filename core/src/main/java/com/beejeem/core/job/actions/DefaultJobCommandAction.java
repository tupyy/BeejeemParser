package com.beejeem.core.job.actions;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.interpreter.CommandInterpreter;
import com.beejeem.core.executor.CommandExecutor;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;
import com.github.oxo42.stateless4j.delegates.Action2;

import java.util.List;

/**
 * This is the default state change action implementation.
 * It calls the command interpreter and submits the job to the executor.
 */
public class DefaultJobCommandAction implements Action2<Command, List<Variable>> {

    private final CommandInterpreter<CommandExecutable> commandInterpreter;
    private final CommandExecutor<CommandExecutable> commandExecutor;

    public DefaultJobCommandAction(CommandInterpreter<CommandExecutable> commandInterpreter, CommandExecutor<CommandExecutable> commandExecutor) {
        this.commandInterpreter = commandInterpreter;
        this.commandExecutor = commandExecutor;
    }
    @Override
    public void doIt(Command command, List<Variable> variables) {
        CommandExecutable commandExecutable = this.commandInterpreter.interpret(command, variables);
        this.commandExecutor.submit(commandExecutable);
    }
}
