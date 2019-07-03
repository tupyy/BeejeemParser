package com.beejeem.parser.test;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.ProgramContext;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.FunctionDefinition;
import com.beejeem.parser.StackFrame;
import com.beejeem.parser.exception.InvalidOperationException;
import com.beejeem.parser.listeners.ProgramListener;
import com.beejeem.parser.type.FloatType;
import com.beejeem.parser.type.IntegerType;
import com.beejeem.parser.value.Value;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;


public class TestParser {

    @Test
    public void testMathOperations() {
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
        assertEquals(false, stackFrame.getVariable("bb1").getValue());
    }

    @Test
    public void testAssignmentErrors() {
        String stringToInt = "int a=\"string\";";
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(new ByteArrayInputStream(stringToInt.getBytes()));
        assertThrows(InvalidOperationException.class, () -> {
            final ProgramListener programListener = new ProgramListener(executionContext);
            programContext.enterRule(programListener);
        });
    }

    @Test
    public void testAssignmentErrors2() {
        String stringToInt = "bool a=\"string\";";
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(new ByteArrayInputStream(stringToInt.getBytes()));
        assertThrows(InvalidOperationException.class, () -> {
            final ProgramListener programListener = new ProgramListener(executionContext);
            programContext.enterRule(programListener);
        });
    }

    @Test
    public void testIfs() {
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
        assertEquals(null, stackFrame.getVariable("i"));
    }

    @Test
    public void testFors() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(readTestFile("forstatements.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();
        assertEquals(5, stackFrame.getVariable("b").getValue());
        assertEquals(4, stackFrame.getVariable("c").getValue());
        assertEquals(450, stackFrame.getVariable("d").getValue());
        assertEquals(4500, stackFrame.getVariable("f").getValue());
    }

    @Test
    public void testFunctions() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(readTestFile("functions.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();

        assertNotEquals(null, stackFrame.getFunctionDefinition("func1"));
        FunctionDefinition func1 = stackFrame.getFunctionDefinition("func1");
        assertEquals(2, func1.getParameters().size());
        assertEquals("x", func1.getParameters().get(0).getName());
        assertEquals("y", func1.getParameters().get(1).getName());
        assertTrue(func1.getParameters().get(0).getParameterType() instanceof IntegerType);
        assertTrue(func1.getParameters().get(1).getParameterType() instanceof IntegerType);
        assertTrue(func1.getResultType() instanceof IntegerType);

        assertNotEquals(null, stackFrame.getFunctionDefinition("func2"));
        FunctionDefinition func2 = stackFrame.getFunctionDefinition("func2");
        assertEquals(0, func2.getParameters().size());
        assertTrue(func2.getResultType() instanceof FloatType);
    }

    private InputStream readTestFile(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    private ProgramContext parse(InputStream stream)  {
        try {
            final Lexer lexer = new bjmLexer(CharStreams.fromStream(stream));
            final TokenStream tokenStream = new CommonTokenStream(lexer);
            final bjmParser parser = new bjmParser(tokenStream);
            return parser.program();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
