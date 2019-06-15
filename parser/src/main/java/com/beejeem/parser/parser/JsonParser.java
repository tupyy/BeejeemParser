package com.beejeem.parser.parser;

import com.beejeem.antrl4.JSONLexer;
import com.beejeem.antrl4.JSONParser;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.visitors.JsonVisitor;
import com.beejeem.parser.parser.visitors.VariableParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.List;

public class JsonParser implements VariableParser {

    @Override
    public List<Variable> parse(String code) throws ParseCancellationException {
        JSONLexer lexer = new JSONLexer(CharStreams.fromString(code));
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        JsonVisitor jsonVisitor = new JsonVisitor();
        List<Variable> traverseResult = jsonVisitor.visit(parser.json());
        return traverseResult;
    }
}
