package com.beejeem.core.command.result;

import com.beejeem.parser.domain.variables.Variable;

import java.util.List;
import java.util.UUID;

public interface CommandResult {

    public enum CommandResultStatus {
        OK,
        ERROR
    }

    public void setCommandResultStatus(CommandResultStatus status);

    /**
     * Get command exit status
     * @return status
     */
    public CommandResultStatus getResultStatus();

    /**
     * Get the id of the command
     * @return id of the command
     */
    public UUID getCommandID();

    /**
     * Get id of the job
     * @return id the job
     */
    public UUID getJobID();

    /**
     * Return the output variables if any
     * @return list of variables
     */
    public List<Variable> getOutputVariables();

    /**
     * Set message
     * @param message string
     */
    public void setMessage(String message);

    /**
     * Get message
     * @return message
     */
    public String getMessage();
    /**
     * set variables
     * @param variables
     */
    public void setVariables(List<Variable> variables);

    public void setVariables(Variable variable);

    public void setVariables(Variable ...variables);




}