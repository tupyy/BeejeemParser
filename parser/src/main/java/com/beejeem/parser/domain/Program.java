package com.beejeem.parser.domain;

import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.Iterator;
import java.util.List;

public interface Program extends Iterator<Command> {

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
