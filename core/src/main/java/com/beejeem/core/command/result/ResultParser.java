package com.beejeem.core.command.result;

import com.beejeem.parser.domain.variables.Variable;

import java.util.List;

public interface ResultParser {

    /**
     * Parse a command out and return a list of variable
     * @param output command out
     * @return list of variables
     */
    public List<Variable> parse(String output);
}
