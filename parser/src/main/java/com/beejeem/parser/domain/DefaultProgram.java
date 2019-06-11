package com.beejeem.parser.domain;


import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class DefaultProgram implements Program {

    private List<Variable> variables = new ArrayList<>();
    private List<Command> commands = new ArrayList<>();

    public DefaultProgram() {}

    public void add(Statement statement) {
        if (statement instanceof Variable) {
            variables.add((Variable) statement);
        } else if (statement instanceof Command) {
            commands.add((Command) statement);
        }
    }

    @Override
    public List<Variable> getVariables() {
        return variables;
    }

    @Override
    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public boolean hasNext() {
        return commands.iterator().hasNext();
    }

    @Override
    public Command next() {
        return commands.iterator().next();
    }
}
