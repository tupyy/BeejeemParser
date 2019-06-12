package com.beejeem.parser.domain;

import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public interface Program extends Iterator<Command> {

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
     * get variables
     * @return list of variables
     */
    public List<Variable> getVariables();

    /**
     * Get commands
     * @return list of commands
     */
    public  List<Command> getCommands();

}
