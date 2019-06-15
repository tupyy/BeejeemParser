package com.beejeem.core.command.interpreter.local;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.GenerateCommandExecutable;
import com.beejeem.core.command.interpreter.AbstractInterpreter;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateInterpreter extends AbstractInterpreter {

    public GenerateInterpreter() {}

    @Override
    public CommandExecutable interpret(Command command, List<Variable> variables) {
        Map<String,Object> vars = variables.stream()
        .collect(Collectors.toMap(Variable::getName, Variable::getValue));
        String templatePath = String.valueOf(command.getVariables().get(0).getValue());

        return new GenerateCommandExecutable(command.getParentID(), command.getID(), templatePath, vars);
    }
}
