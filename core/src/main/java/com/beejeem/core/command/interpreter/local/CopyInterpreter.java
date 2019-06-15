package com.beejeem.core.command.interpreter.local;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.CopyCommandExecutable;
import com.beejeem.core.command.interpreter.AbstractInterpreter;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CopyInterpreter extends AbstractInterpreter {

    public CopyInterpreter() {
    }

    @Override
    public CommandExecutable interpret(Command command, List<Variable> variables) {

        Path sourceFile = Paths.get(String.valueOf(command.getVariables().get(0).getValue()));
        Path destination = Paths.get(String.valueOf(command.getVariables().get(1).getValue()));
        return new CopyCommandExecutable(command.getParentID(), command.getID(), sourceFile, destination);
    }
}
