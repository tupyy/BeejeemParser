package com.beejeem.parser.parser;

import com.beejeem.antrl4.visitor.JobfileLexer;
import com.beejeem.antrl4.visitor.JobfileParser;
import com.beejeem.parser.domain.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class DefaultParser implements Parser {

    @Override
    public Program parse(String code) {
        JobfileLexer lexer = new JobfileLexer(CharStreams.fromString(code));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JobfileParser parser = new JobfileParser(tokenStream);

        DefaultJobfileVisitor defaultJobfileVisitor = new DefaultJobfileVisitor();
        Program traverseResult = defaultJobfileVisitor.visit(parser.program());
        return traverseResult;
    }
}
