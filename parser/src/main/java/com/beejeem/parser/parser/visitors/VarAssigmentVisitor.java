package com.beejeem.parser.parser.visitors;

import com.beejeem.antrl4.visitor.JobfileBaseVisitor;
import com.beejeem.antrl4.visitor.JobfileParser;
import com.beejeem.parser.domain.variables.Variable;

public class VarAssigmentVisitor extends JobfileBaseVisitor<Variable> {

    public Variable visitVarassignment(JobfileParser.VarassignmentContext ctx) {
        Variable variable = null;
        VariableVisitor variableVisitor;
        String varName = ctx.varname().getText();

        JobfileParser.VarvalueContext varvalueContext = ctx.varvalue();
        if (varvalueContext.string() != null) {
            variable = varvalueContext.string().accept(new StringVisitor(varName));
        } else if (varvalueContext.number() != null) {
            variable = varvalueContext.number().accept(new NumberVisitor(varName));
        } else {
            variable = varvalueContext.list().accept(new ListVisitor(varName));
        }
        return variable;
    }


}
