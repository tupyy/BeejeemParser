package com.beejeem.parser.domain;


import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public final class DefaultProgram implements Program {

    private List<Variable> variables = new ArrayList<>();
    private List<Command> commands = new ArrayList<>();
    private Iterator<Command> commandIterator = null;
    private final UUID id = UUID.randomUUID();

    public DefaultProgram() {

    }

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
        return variables.stream().map(Variable::clone).collect(Collectors.toList());
    }

    @Override
    public void updateVariable(Variable newVariable) {
        checkNotNull(newVariable);

        Variable v = variables.stream()
                              .filter(variable -> variable.getName().equals(newVariable.getName()))
                              .findAny()
                              .orElse(null);
        if (v != null) {
            v.setValue(newVariable.getValue());
        }
    }

    @Override
    public List<Command> getCommands() {
        return commands.stream().map(Command::clone).collect(Collectors.toList());
    }

    @Override
    public Iterator<Command> getIterator() {
        if (this.commandIterator == null) {
            this.commandIterator = this.commands.iterator();
        }
        return this.commandIterator;
    }

}
