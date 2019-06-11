package com.beejeem.parser.parser.visitors;

import com.beejeem.parser.domain.Statement;
import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import org.antlr.v4.runtime.tree.ParseTree;

public class LineVisitor extends JobfileBaseVisitor<Statement> {

    public Statement visitLine(JobfileParser.LineContext ctx) {
        for(ParseTree parseTree: ctx.children) {
            if (parseTree instanceof JobfileParser.StatementContext) {
                return parseTree.accept(new StatementVisitor());
            }
        }

        return null;
    }
}
