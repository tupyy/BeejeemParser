package com.beejeem.parser.parser;

import com.beejeem.parser.domain.Program;
import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.visitors.VarAssigmentVisitor;

public class DefaultJobfileVisitor extends JobfileBaseVisitor<Program> {

    @Override
    public Program visitProgram(JobfileParser.ProgramContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public Program visitVarstmt(JobfileParser.VarstmtContext ctx) {
        VarAssigmentVisitor variableVarAssigmentVisitor = new VarAssigmentVisitor();
        Variable  variable = ctx.varassignment().accept(variableVarAssigmentVisitor);
        return null;
    }
}
