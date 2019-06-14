package com.beejeem.core.result;

import com.beejeem.parser.domain.variables.Variable;

import java.util.*;

public class CommandResultImpl implements CommandResult {

    private CommandResultStatus status = CommandResultStatus.OK; // default value
    private final UUID commandID;
    private final UUID jobID;
    private  List<Variable> outputVariables;

    public CommandResultImpl(UUID jobID, UUID commandID) {
        this.jobID = jobID;
        this.commandID = commandID;
    }

    public CommandResultImpl(UUID jobID, UUID commandID, CommandResultStatus status) {
        this(jobID,commandID);
        this.status = status;
    }

    public CommandResultImpl(UUID jobID, UUID commandID, CommandResultStatus status, List<Variable> outputVariables) {
        this(jobID,commandID,status);
        this.outputVariables = outputVariables;
    }

    @Override
    public void setCommandResultStatus(CommandResultStatus status) {
        this.status = status;
    }

    @Override
    public CommandResultStatus getResultStatus() {
        return this.status;
    }

    @Override
    public UUID getCommandID() {
        return this.commandID;
    }

    @Override
    public UUID getJobID() {
        return this.jobID;
    }

    @Override
    public List<Variable> getOutputVariables() {
        if (this.outputVariables == null) {
            return new ArrayList<>();
        }

        return this.outputVariables;
    }

    @Override
    public void setVariables(List<Variable> variables) {
        if (this.outputVariables == null) {
            this.outputVariables = variables;
        } else {
            this.outputVariables.addAll(variables);
        }
    }

    @Override
    public void setVariables(Variable variable) {
        if (this.outputVariables == null) {
            this.outputVariables = Arrays.asList(variable);
        } else {
            this.outputVariables.add(variable);
        }
    }

    @Override
    public void setVariables(Variable... variables) {
        if (this.outputVariables == null) {
            this.outputVariables = Arrays.asList(variables);
        } else {
            this.outputVariables.addAll(Arrays.asList(variables));
        }

    }
}
