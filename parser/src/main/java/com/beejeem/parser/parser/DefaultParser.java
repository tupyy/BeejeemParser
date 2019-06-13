package com.beejeem.parser.parser;

import com.beejeem.antrl4.JobfileLexer;
import com.beejeem.antrl4.JobfileParser;
import com.beejeem.parser.domain.Program;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DefaultParser implements Parser {

    private static Logger logger = LogManager.getLogger(DefaultParser.class);

    @Override
    public Program parse(String code) throws ParseCancellationException {
        JobfileLexer lexer = new JobfileLexer(CharStreams.fromString(code));
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JobfileParser parser = new JobfileParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        DefaultJobfileVisitor defaultJobfileVisitor = new DefaultJobfileVisitor();

        logger.debug("Start parsing");
        Program traverseResult = defaultJobfileVisitor.visit(parser.program());
        return traverseResult;
    }
}
