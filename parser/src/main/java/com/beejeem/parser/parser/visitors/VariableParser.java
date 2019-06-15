package com.beejeem.parser.parser.visitors;

import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.Parser;

import java.util.List;

public interface VariableParser extends Parser<List<Variable>> {

    @Override
    public List<Variable> parse(String data);
}
