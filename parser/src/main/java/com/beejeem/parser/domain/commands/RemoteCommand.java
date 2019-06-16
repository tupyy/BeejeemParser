package com.beejeem.parser.domain.commands;

import com.beejeem.parser.domain.variables.Variable;

import java.util.UUID;

public class RemoteCommand extends AbstractCommand {

    public RemoteCommand(UUID parentID, CommandType type) {
        super(parentID, type);
    }

    public RemoteCommand(CommandType type) {
        super(type);
    }

    @Override
    public Boolean isRemote() {
        return true;
    }

    @Override
    public Command clone() {
        Command clone = new RemoteCommand(this.getParentID(), this.getType());
        for (Variable variable: this.getVariables()) {
            clone.add(variable.clone());
        }
        return clone;
    }
}
