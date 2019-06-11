package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.parser.domain.variables.*;
import com.beejeem.antrl4.JobfileParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.stream.Collectors;

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
        return new StringVariable(this.getVariableName(),ctx.string_content().getText());
    }

    public Variable visitBool(JobfileParser.BoolContext ctx) {
        return new BooleanVariable(this.getVariableName(), Boolean.valueOf(ctx.getText()));
    }
}
