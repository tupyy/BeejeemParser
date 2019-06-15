package com.beejeem.core.command.interpreter;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.interpreter.local.CopyInterpreter;
import com.beejeem.core.command.interpreter.local.GenerateInterpreter;
import com.beejeem.core.command.interpreter.local.LocalRunInterpreter;
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
                case GENERATE:
                    return new GenerateInterpreter().interpret(command,variables);
            }
        }
        return null;
    }
}
