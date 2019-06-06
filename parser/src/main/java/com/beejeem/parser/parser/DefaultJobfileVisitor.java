package com.beejeem.parser.parser;

import com.beejeem.parser.domain.Program;
import com.beejeem.antrl4.visitor.JobfileBaseVisitor;
import com.beejeem.antrl4.visitor.JobfileParser;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.visitors.VarAssigmentVisitor;

public class DefaultJobfileVisitor extends JobfileBaseVisitor<Program> {

    @Override
    public Program visitProgram(JobfileParser.ProgramContext ctx) {
        visitChildren(ctx);
        return null;
    }

    public Program visitVarstmt(JobfileParser.VarstmtContext ctx) {
        System.out.println(ctx.getText());

        VarAssigmentVisitor variableVarAssigmentVisitor = new VarAssigmentVisitor();
        Variable  variable = ctx.varassignment().accept(variableVarAssigmentVisitor);
        System.out.println(variable.getName());
        return null;
    }
}
