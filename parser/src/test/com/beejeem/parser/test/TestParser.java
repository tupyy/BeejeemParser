package com.beejeem.parser.test;

import com.beejeem.grammar.bjmLexer;
import com.beejeem.grammar.bjmParser;
import com.beejeem.grammar.bjmParser.ProgramContext;
import com.beejeem.parser.ExecutionContext;
import com.beejeem.parser.function.UserDefinedFunction;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestParser {

    @Test
    public void testMathOperations() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(readTestFile("math_op.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();
        Value var = (Value)stackFrame.getVariable("c");
        assertEquals(1f, var.get());
        assertEquals(8f, ((Value)stackFrame.getVariable("cc")).get());
        assertEquals(4f, ((Value)stackFrame.getVariable("p")).get());
        assertEquals(24, ((Value)stackFrame.getVariable("i1")).get());
        assertEquals(2, ((Value)stackFrame.getVariable("b")).get());
        assertEquals(2, ((Value)stackFrame.getVariable("a")).get());
        assertEquals(2, ((Value)stackFrame.getVariable("aa")).get());
        assertEquals(false, ((Value)stackFrame.getVariable("bb1")).get());
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
        assertEquals(2, ((Value)stackFrame.getVariable("c")).get());
        assertEquals(1, ((Value)stackFrame.getVariable("d")).get());
        assertEquals(0, ((Value)stackFrame.getVariable("e")).get());
        assertEquals(1, ((Value)stackFrame.getVariable("f")).get());
        assertEquals(1, ((Value)stackFrame.getVariable("g")).get());
        assertEquals(1, ((Value)stackFrame.getVariable("h")).get());
        assertNull(stackFrame.getVariable("i"));
    }

    @Test
    public void testFors() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(readTestFile("forstatements.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();
        assertEquals(5, ((Value)stackFrame.getVariable("b")).get());
        assertEquals(4, ((Value)stackFrame.getVariable("c")).get());
        assertEquals(450, ((Value)stackFrame.getVariable("d")).get());
        assertEquals(4500, ((Value)stackFrame.getVariable("f")).get());
    }

    @Test
    public void testFunctions() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext =this.parse(readTestFile("functions.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();

        assertNotEquals(null, stackFrame.getFunctionDefinition("func1"));
        UserDefinedFunction func1 = stackFrame.getFunctionDefinition("func1");
        assertEquals(2, func1.getParameters().size());
        assertEquals("x", func1.getParameters().get(0).getName());
        assertEquals("y", func1.getParameters().get(1).getName());
        assertTrue(func1.getParameters().get(0).getParameterType() instanceof IntegerType);
        assertTrue(func1.getParameters().get(1).getParameterType() instanceof IntegerType);
        assertTrue(func1.getResultType() instanceof IntegerType);

        assertNotEquals(null, stackFrame.getFunctionDefinition("func2"));
        UserDefinedFunction func2 = stackFrame.getFunctionDefinition("func2");
        assertEquals(0, func2.getParameters().size());
        assertTrue(func2.getResultType() instanceof FloatType);
    }

    @Test
    public void testFunctions2() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext = this.parse(readTestFile("functions.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();
        assertEquals(4, ((Value)stackFrame.getVariable("a")).get());
        assertEquals(2f, ((Value)stackFrame.getVariable("b")).get());
        assertEquals("test func3", ((Value)stackFrame.getVariable("c")).get());
        assertEquals(false, ((Value)stackFrame.getVariable("d")).get());
    }

    @Test
    public void testLists() {
        ExecutionContext executionContext = new ExecutionContext();
        ProgramContext programContext = this.parse(readTestFile("lists.txt"));
        final ProgramListener programListener = new ProgramListener(executionContext);
        programContext.enterRule(programListener);

        StackFrame stackFrame = programListener.getLastStack();
        assertEquals(3, ((List<?>)stackFrame.getVariable("a")).size());

        assertEquals(4, ((List<?>)stackFrame.getVariable("b")).size());

        List<?> myList = (List<?>) stackFrame.getVariable("b");
        Value b = (Value) myList.get(0);
        assertEquals(1, b.get());

        assertEquals(3, ((List<?>)stackFrame.getVariable("c")).size());

        List<?> myList1 = (List<?>) stackFrame.getVariable("c");
        Value c = (Value) myList1.get(0);
        assertEquals(true, c.get());

        assertEquals(0, ((List<?>)stackFrame.getVariable("d")).size());
        assertEquals(1, ((Value)stackFrame.getVariable("aa")).get());
        assertEquals(1, ((Value)stackFrame.getVariable("i")).get());
        assertEquals(4, ((Value)stackFrame.getVariable("sum")).get());
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
