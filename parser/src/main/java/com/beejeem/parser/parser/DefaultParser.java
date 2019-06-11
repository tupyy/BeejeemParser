package com.beejeem.parser.parser;

import com.beejeem.antrl4.JobfileLexer;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.DefaultProgram;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class DefaultParser implements Parser {

    @Override
    public DefaultProgram parse(String code) {
        JobfileLexer lexer = new JobfileLexer(CharStreams.fromString(code));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JobfileParser parser = new JobfileParser(tokenStream);

        DefaultJobfileVisitor defaultJobfileVisitor = new DefaultJobfileVisitor();
        DefaultProgram traverseResult = defaultJobfileVisitor.visit(parser.program());
        return traverseResult;
    }
}
