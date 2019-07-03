package com.beejeem.parser.test;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.ProgramContext;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.StackFrame;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.ProgramListener;
import com.beejeem.parser.value.Value;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestParser {

    @Test
    public void testMathOperations() {
        try {
            ExecutionContext executionContext = new ExecutionContext();
            ProgramContext programContext =this.parse(readTestFile("math_op.txt"));
            final ProgramListener programListener = new ProgramListener(executionContext);
            programContext.enterRule(programListener);

            StackFrame stackFrame = programListener.getLastStack();
            Value var = stackFrame.getVariable("c");
            assertEquals(1f, var.getValue());
            assertEquals(8f, stackFrame.getVariable("cc").getValue());
            assertEquals(4f, stackFrame.getVariable("p").getValue());
            assertEquals(24, stackFrame.getVariable("i1").getValue());
            assertEquals(false, stackFrame.getVariable("b1").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAssignmentErrors() {
        String stringToInt = "int a=\"string\";";
        try {
            ExecutionContext executionContext = new ExecutionContext();
            ProgramContext programContext =this.parse(new ByteArrayInputStream(stringToInt.getBytes()));
            assertThrows(InvalidOperationException.class, () -> {
                final ProgramListener programListener = new ProgramListener(executionContext);
                programContext.enterRule(programListener);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAssignmentErrors2() {
        String stringToInt = "bool a=\"string\";";
        try {
            ExecutionContext executionContext = new ExecutionContext();
            ProgramContext programContext =this.parse(new ByteArrayInputStream(stringToInt.getBytes()));
            assertThrows(InvalidOperationException.class, () -> {
                final ProgramListener programListener = new ProgramListener(executionContext);
                programContext.enterRule(programListener);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIfs() {
        try {
            ExecutionContext executionContext = new ExecutionContext();
            ProgramContext programContext =this.parse(readTestFile("ifstatements.txt"));
            final ProgramListener programListener = new ProgramListener(executionContext);
            programContext.enterRule(programListener);

            StackFrame stackFrame = programListener.getLastStack();
            assertEquals(2, stackFrame.getVariable("c").getValue());
            assertEquals(1, stackFrame.getVariable("d").getValue());
            assertEquals(0, stackFrame.getVariable("e").getValue());
            assertEquals(1, stackFrame.getVariable("f").getValue());
            assertEquals(1, stackFrame.getVariable("g").getValue());
            assertEquals(1, stackFrame.getVariable("h").getValue());
            assertEquals(1, stackFrame.getVariable("i").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream readTestFile(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    private ProgramContext parse(InputStream stream) throws IOException {
        final Lexer lexer = new bjmLexer(CharStreams.fromStream(stream));
        final TokenStream tokenStream = new CommonTokenStream(lexer);
        final bjmParser parser = new bjmParser(tokenStream);
        return parser.program();
    }

}
