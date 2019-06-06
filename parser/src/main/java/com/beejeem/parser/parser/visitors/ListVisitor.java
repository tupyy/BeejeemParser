package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.visitor.JobfileParser;
import com.beejeem.parser.domain.variables.Variable;

public class ListVisitor extends VariableVisitor {

    public ListVisitor(String varName) {
        super(varName);
    }

    public Variable visitList(JobfileParser.ListContext ctx) {
        Variable variable = null;
        return variable;
    }
}
