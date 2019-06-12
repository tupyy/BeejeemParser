package com.beejeem.core.interpreter;

import com.beejeem.core.executable.CommandExecutable;
import com.beejeem.core.interpreter.local.CopyInterpreter;
import com.beejeem.core.interpreter.local.LocalRunInterpreter;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.commands.LocalCommand;
import com.beejeem.parser.domain.variables.Variable;

import java.util.List;

public class DefaultCommandInterpreter implements CommandInterpreter<CommandExecutable> {

    public DefaultCommandInterpreter() {}

    @Override
    public CommandExecutable interpret(Command command, List<Variable> variables) {
        if (command instanceof LocalCommand) {
            switch (command.getType()) {
                case COPY:
                    return new CopyInterpreter().interpret(command, variables);
                case RUN:
                    return new LocalRunInterpreter().interpret(command, variables);
            }
        }
        return null;
    }
}
