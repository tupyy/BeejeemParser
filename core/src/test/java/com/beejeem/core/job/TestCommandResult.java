package com.beejeem.core.job;

import com.beejeem.core.result.CommandResult;
import com.beejeem.parser.domain.variables.Variable;

import java.util.List;
import java.util.UUID;

public class TestCommandResult implements CommandResult {

    private final CommandResultStatus status;
    private final UUID commandID;

    public TestCommandResult(UUID commandID, CommandResultStatus status) {
        this.commandID = commandID;
        this.status = status;
    }

    @Override
    public void setCommandResultStatus(CommandResultStatus status) {

    }

    @Override
    public CommandResultStatus getResultStatus() {
        return status;
    }

    @Override
    public UUID getCommandID() {
        return this.commandID;
    }

    @Override
    public UUID getJobID() {
        return null;
    }

    @Override
    public List<Variable> getOutputVariables() {
        return null;
    }

    @Override
    public void setVariables(List<Variable> variables) {

    }

    @Override
    public void setVariables(Variable variable) {

    }

    @Override
    public void setVariables(Variable... variables) {

    }
}
