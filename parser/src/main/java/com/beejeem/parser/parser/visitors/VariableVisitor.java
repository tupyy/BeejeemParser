package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.visitor.JobfileBaseVisitor;
import com.beejeem.parser.domain.variables.FloatVariable;
import com.beejeem.parser.domain.variables.IntegerVariable;
import com.beejeem.parser.domain.variables.StringVariable;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.antrl4.visitor.JobfileParser;

public class VariableVisitor extends JobfileBaseVisitor<Variable> {

    private final String varName;

    public VariableVisitor(String varName) {
        this.varName = varName;
    }

    public String getVariableName() {
        return this.varName;
    }

    public Variable visitVarvalue(JobfileParser.VarvalueContext ctx) {
        Variable v = visitChildren(ctx);
        return v;
    }

    public Variable visitNumber(JobfileParser.NumberContext ctx) {
        Variable variable;
        if (ctx.FLOAT() != null) {
            variable = new FloatVariable(this.getVariableName(), Float.valueOf(ctx.FLOAT().getText()));
        } else {
            variable = new IntegerVariable(this.getVariableName(), Integer.valueOf(ctx.INT().getText()));
        }
        return variable;
    }

    public Variable visitString(JobfileParser.StringContext ctx) {
        return new StringVariable(this.getVariableName(),ctx.getText());
    }
}
