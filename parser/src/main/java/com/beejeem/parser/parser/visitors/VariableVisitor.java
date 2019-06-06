package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.visitor.JobfileBaseVisitor;
import com.beejeem.parser.domain.variables.*;
import com.beejeem.antrl4.visitor.JobfileParser;
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
        return new StringVariable(this.getVariableName(),ctx.getText());
    }

    public Variable visitList(JobfileParser.ListContext ctx) {
        VariableVisitor variableVisitor = new VariableVisitor(this.varName);
        List<Variable> variables = ctx.elements().children
                .stream()
                .filter(v -> !(v instanceof TerminalNode))
                .map(v -> v.accept(variableVisitor))
                .collect(Collectors.toList());

        ListVariable<Variable> variable = new ListVariable<>(this.varName);
        variable.add(variables);
        return variable;
    }
}
