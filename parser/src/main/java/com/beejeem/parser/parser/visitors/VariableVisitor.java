package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.visitor.JobfileBaseVisitor;
import com.beejeem.parser.domain.variables.Variable;

public class VariableVisitor extends JobfileBaseVisitor<Variable> {

    private final String varName;

    public VariableVisitor(String varName) {
        this.varName = varName;
    }

    public String getVariableName() {
        return this.varName;
    }
}
