package com.beejeem.core.command.interpreter;

import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.List;

/**
 * Interface for command interpreter.
 * A command interpreter is a class which will transform the command object into an executable class.
 *
 * For local command types, the executable class is a class which will execute a local process based on the type of the
 * command (e.g for {@link com.beejeem.parser.domain.commands.Command.CommandType} Copy which will be executed on linux
 * a "cp" command will be executed.
 *
 * For remote command types, a ssh session must be created first and than a remote command will be executed
 * @param <T>
 */
public interface CommandInterpreter<T> {

    public T interpret(Command command, List<Variable> variables);
}
