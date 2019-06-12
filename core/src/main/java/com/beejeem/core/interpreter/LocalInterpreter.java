package com.beejeem.core.interpreter;

import com.beejeem.core.executable.CommandExecutable;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.List;

public class LocalInterpreter extends AbstractInterpreter {

    @Override
    public CommandExecutable interpret(Command command, List<Variable> variables) {
        return null;
    }
}
