package com.beejeem.parser.domain;


import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public final class DefaultProgram implements Program {

    private static Logger logger = LogManager.getLogger(DefaultProgram.class);

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
            Variable v = (Variable) statement;
            if ( !this.hasVariable(v) ) {
                variables.add(v);
            } else {
                this.updateVariable(v);
            }
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
            if (v.getClass().equals(newVariable.getClass())) {
                v.setValue(newVariable.getValue());
            } else {
                logger.warn("You are updating a variable with a variable of different class." +
                        "The old variable will be replaced by the new one.");
                this.variables.remove(v);
                this.add(newVariable);
            }
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

    private boolean hasVariable(Variable variable) {
        Variable v = variables.stream()
                .filter(vv -> vv.getName().equals(variable.getName()))
                .findAny()
                .orElse(null);
        return v != null;
    }

}
