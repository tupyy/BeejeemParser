package com.beejeem.parser.domain.commands;

import com.beejeem.parser.domain.variables.Variable;

import java.util.UUID;

public class LocalCommand extends AbstractCommand {

    public LocalCommand(UUID parentID, CommandType type) {
        super(parentID, type);
    }

    public LocalCommand(CommandType type) {
        super(type);
    }

    @Override
    public Boolean isRemote() {
        return false;
    }

    @Override
    public Command clone() {
        Command clone = new LocalCommand(this.getParentID(), this.getType());
        for (Variable variable: this.getVariables()) {
            clone.add(variable.clone());
        }
        return clone;
    }
}
