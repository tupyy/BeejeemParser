package com.beejeem.parser.domain.commands;

import com.beejeem.parser.domain.variables.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractCommand implements Command {

    private final UUID id = java.util.UUID.randomUUID();
    private UUID parentID;
    private final CommandType type;
    private List<Variable> variables = new ArrayList<>();

    public AbstractCommand(UUID parentID, CommandType type) {
        this.parentID = parentID;
        this.type = type;
    }

    public AbstractCommand(CommandType type) {
        this.type = type;
    }

    @Override
    public UUID getID() {
        return this.id;
    }

    @Override
    public UUID getParentID() {
        return this.parentID;
    }

    @Override
    public void setParentID(UUID parentID) {
        this.parentID = parentID;
    }

    @Override
    public CommandType getType() {
        return type;
    }

    @Override
    public List<Variable> getVariables() {
        return this.variables;
    }

    @Override
    public void add(Variable variable) {
        checkNotNull(variable, "Received null instead of variables");
        this.variables.add(variable);
    }

    @Override
    public void add(List<Variable> variables) {
        checkNotNull(variables, "Received null instead of list of variables");
        this.variables.addAll(variables);
    }

    @Override
    public Variable get(String variableName) {
        checkNotNull(variableName, "Received null instead variable name");
        return this.getVariable(variableName);
    }

    @Override
    public void remove(String variableName) {
        checkNotNull(variableName, "Received null instead variable name");
        Variable v = this.getVariable(variableName);
        if (v != null) {
            this.variables.remove(v);
        }
    }

    @Override
    public void clear() {
        this.variables.clear();
    }

    @Override
    public abstract Boolean isRemote();

    private Variable getVariable(String variableName) {
        return (Variable) this.variables.stream().filter(variable -> variable.getName().equals(variableName));
    }
}
