package com.beejeem.core.command.result;

import com.beejeem.parser.domain.variables.Variable;

import java.util.*;

public class CommandResultImpl implements CommandResult {

    private CommandResultStatus status = CommandResultStatus.OK; // default value
    private String message = "";
    private final UUID commandID;
    private final UUID jobID;
    private  List<Variable> outputVariables = new ArrayList<>();

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

    public CommandResultImpl(UUID jobID, UUID commandID, CommandResultStatus status, String message) {
        this(jobID, commandID, status);
        this.message = message;
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
              return this.outputVariables;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setVariables(List<Variable> variables) {
        this.outputVariables.addAll(variables);
    }

    @Override
    public void setVariables(Variable variable) {
        this.outputVariables.add(variable);

    }

    @Override
    public void setVariables(Variable... variables) {
        this.outputVariables.addAll(Arrays.asList(variables));
    }
}
