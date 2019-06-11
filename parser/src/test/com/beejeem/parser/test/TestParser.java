package com.beejeem.parser.test;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.commands.LocalCommand;
import com.beejeem.parser.domain.variables.*;
import com.beejeem.parser.parser.DefaultParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class TestParser {

    @Test
    public void testStringVariable() {
        String code = "x=\"test\"\n";
        final Program result = new DefaultParser().parse(code);
        Assert.assertEquals(1, result.getVariables().size());

        List<Variable> variables = result.getVariables();

        Assert.assertTrue(variables.get(0) instanceof StringVariable);
        StringVariable s = (StringVariable) variables.get(0);
        Assert.assertEquals("x", s.getName());
        Assert.assertEquals("test", s.getValue());
    }

    @Test
    public void testIntegerVariable() {
        String code = "x=2\n";
        final Program result = new DefaultParser().parse(code);
        Assert.assertEquals(1, result.getVariables().size());

        List<Variable> variables = result.getVariables();

        Assert.assertTrue(variables.get(0) instanceof IntegerVariable);
        IntegerVariable s = (IntegerVariable) variables.get(0);
        Assert.assertEquals("x", s.getName());
        Assert.assertEquals(Integer.valueOf(2), s.getValue());
    }

    @Test
    public void testFloatVariable() {
        String code = "x=2.2\n";
        final Program result = new DefaultParser().parse(code);
        Assert.assertEquals(1, result.getVariables().size());

        List<Variable> variables = result.getVariables();

        Assert.assertTrue(variables.get(0) instanceof FloatVariable);
        Assert.assertEquals("x", variables.get(0).getName());
        Assert.assertEquals(2.2f, variables.get(0).getValue());
    }

    @Test
    public void testBooleanVariable() {
        String code = "x=true\n";
        final Program result = new DefaultParser().parse(code);
        Assert.assertEquals(1, result.getVariables().size());

        List<Variable> variables = result.getVariables();

        Assert.assertTrue(variables.get(0) instanceof BooleanVariable);
        Assert.assertEquals("x", variables.get(0).getName());
        Assert.assertTrue((Boolean) variables.get(0).getValue());
    }

    @Test
    public void testMultipleVariable() {
        String code = "x=\"test\"\ny=2\n";
        final Program result = new DefaultParser().parse(code);
        Assert.assertEquals(2, result.getVariables().size());

        List<Variable> variables = result.getVariables();

        Assert.assertTrue(variables.get(0) instanceof StringVariable);
        StringVariable s = (StringVariable) variables.get(0);
        Assert.assertEquals("x", s.getName());
        Assert.assertEquals("test", s.getValue());

        Assert.assertTrue(variables.get(1) instanceof IntegerVariable);
        Assert.assertEquals("y", variables.get(1).getName());
        Assert.assertEquals(2, variables.get(1).getValue());
    }

    @Test
    public void testRunCommand() {
        String code = "run \"arg\"\n";
        final Program result = new DefaultParser().parse(code);
        Assert.assertEquals(0, result.getVariables().size());
        Assert.assertEquals(1, result.getCommands().size());

        List<Command> commands = result.getCommands();

        Assert.assertTrue(commands.get(0) instanceof LocalCommand);
        Assert.assertEquals(Command.CommandType.RUN, commands.get(0).getType()  );
        Assert.assertEquals(1, commands.get(0).getVariables().size());
        Assert.assertEquals("arg", commands.get(0).getVariables().get(0).getValue());
    }

    @Test(expected = ParseCancellationException.class)
    public void testParseException() {
        String code = "run \"arg\"\nunknown_command \"ddd\"\n";
        final Program result = new DefaultParser().parse(code);
    }
}
