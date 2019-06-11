package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.variables.Variable;

public class VarAssigmentVisitor extends JobfileBaseVisitor<Variable> {

    public Variable visitVarassignment(JobfileParser.VarassignmentContext ctx) {
        String varName = ctx.varname().getText();
        return ctx.varvalue().accept(new VariableVisitor(varName));
    }
}
