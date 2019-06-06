package com.beejeem.parser.parser;

import com.beejeem.parser.domain.Program;
import com.beejeem.antrl4.visitor.JobfileBaseVisitor;

public class DefaultJobfileVisitor implements Parser {

    @Override
    public Program parse(String code) {
        return null;
    }

    private static class ProgramVisitor extends JobfileBaseVisitor<Program> {

    }
}
