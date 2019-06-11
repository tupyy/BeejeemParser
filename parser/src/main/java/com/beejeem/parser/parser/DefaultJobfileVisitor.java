package com.beejeem.parser.parser;

import com.beejeem.antrl4.JobfileBaseVisitor;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.Statement;
import com.beejeem.parser.parser.visitors.LineVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultJobfileVisitor extends JobfileBaseVisitor<Program> {

    private Program program = new Program();

    @Override
    public Program visitProgram(JobfileParser.ProgramContext ctx) {
        List<Statement> statements = ctx.children.stream()
                                                 .map(lineCtx -> lineCtx.accept(new LineVisitor()))
                                                 .collect(Collectors.toList());
        for (Statement statement: statements) {
            program.add(statement);
        }
        return program;
    }
}
