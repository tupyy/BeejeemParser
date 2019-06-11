package com.beejeem.parser.domain;


import com.beejeem.parser.domain.variables.Variable;

import java.util.ArrayList;
import java.util.List;

public class Program {

    List<Variable> variables = new ArrayList<>();

    public Program() {}

    public void add(Statement statement) {
        if (statement instanceof Variable) {
            variables.add((Variable) statement);
        }
    }
}
