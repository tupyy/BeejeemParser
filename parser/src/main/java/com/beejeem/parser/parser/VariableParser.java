package com.beejeem.parser.parser;

import com.beejeem.parser.domain.variables.Variable;

import java.util.List;

public interface VariableParser extends Parser<List<Variable>> {

    @Override
    public List<Variable> parse(String data);
}
