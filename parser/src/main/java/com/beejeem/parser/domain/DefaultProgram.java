package com.beejeem.parser.domain;


import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DefaultProgram implements Program {

    private List<Variable> variables = new ArrayList<>();
    private List<Command> commands = new ArrayList<>();
    private final UUID id = UUID.randomUUID();

    public DefaultProgram() {}

    @Override
    public UUID getID() {
        return this.id;
    }

    public void add(Statement statement) {
        if (statement instanceof Variable) {
            variables.add((Variable) statement);
        } else if (statement instanceof Command) {
            Command command = (Command) statement;
            command.setParentID(this.id);
            commands.add(command);
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
}
