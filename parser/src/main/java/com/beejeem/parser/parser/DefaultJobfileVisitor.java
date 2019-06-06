package com.beejeem.parser.parser;

import com.beejeem.parser.domain.Program;
import com.beejeem.antrl4.visitor.JobfileBaseVisitor;
import com.beejeem.antrl4.visitor.JobfileParser;

public class DefaultJobfileVisitor extends JobfileBaseVisitor<Program> {

    @Override
    public Program visitProgram(JobfileParser.ProgramContext ctx) {
        return null;
    }
}
