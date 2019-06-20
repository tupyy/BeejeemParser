package com.beejeem.parser.domain;

import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public interface Program {

    /**
     * Get program ID
     * @return id of the program
     */
    public UUID getID();

    /**
     * Add statement
     * A statement can be a variable assignment or a command
     * @param statement statement to be added
     */
    public void add(Statement statement);

    /**
     * Get a list of cloned variables.
     * @return list of cloned variables
     */
    public List<Variable> getVariables();

    /**
     * Update the value of the variable
     * @param newVariable new variable
     */
    public void updateVariable(Variable newVariable);

    /**
     * Get the list of cloned commands.
     * @return list of cloned commands.
     */
    public  List<Command> getCommands();

    public Iterator<Command> getIterator();

    /**
     * Clone
     * @return clone
     */
    public Program clone();

}
