package domain.commands;

import domain.variables.Variable;

import java.util.List;
import java.util.UUID;

public interface Command {

    public enum CommandType {
        RUN,
        SUBMIT,
        COPY,
        RCOPY,
        GENERATE
    }

    /**
     * Return the ID of the command
     * @return command id
     */
    public UUID getID();

    /**
     * Return the ID of the parent
     * @return id of the parent
     */
    public UUID getParentID();

    /**
     * Return the type of the command
     * @return type of command
     */
    public CommandType getType();

    /**
     * Return the list of variables
     * @return list of variables
     */
    public List<Variable> getVariables();

    /**
     * Add variable
     * @param variable
     */
    public void add(Variable variable);

    /**
     * Add a list of variables
     * @param variables
     */
    public void add(List<Variable> variables);

    /**
     * Get variable
     * @param variableName
     * @return variable or null if not found
     */
    public Variable get(String variableName);

    /**
     * Remove variable
     * @param variableName variable name
     */
    public void remove(String variableName);

    /**
     * Clear all variables
     */
    public void clear();

    /**
     * Return true if is a command to be executed remotely
     * @return true if remote
     */
    public Boolean isRemote();
}