package com.beejeem.parser.parser.visitors;

import com.beejeem.parser.domain.Statement;
import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;
import org.antlr.v4.runtime.tree.ParseTree;

public class StatementVisitor extends JobfileBaseVisitor<Statement> {

    public Statement visitStatement(JobfileParser.StatementContext ctx) {
        for (ParseTree parseTree: ctx.children) {
            if (parseTree instanceof JobfileParser.VarassignmentContext) {
                Variable variable = parseTree.accept(new VarAssigmentVisitor());
                return variable;
            } else {
                Command command = parseTree.accept(new CommandVisitor());
                return command;
            }
        }

        return null;
    }
}
