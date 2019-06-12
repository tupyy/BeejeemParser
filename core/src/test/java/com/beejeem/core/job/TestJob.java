package com.beejeem.core.job;

import com.beejeem.parser.domain.DefaultProgram;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.commands.LocalCommand;
import com.beejeem.parser.domain.variables.StringVariable;
import org.junit.Assert;
import org.junit.Test;

public class TestJob {

    private DefaultJob job;
    private TestCommandResult okResult = new TestCommandResult(CommandResult.CommandResultStatus.OK);
    private TestCommandResult errorResult = new TestCommandResult(CommandResult.CommandResultStatus.ERROR);
    private TestCommandAction testCommandAction = new TestCommandAction();

    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testNominal() {
        Program program = new DefaultProgram();
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.processCommandResult(okResult);
        Assert.assertEquals(job.getState(), program.getCommands().get(1).getID());
        job.processCommandResult(okResult);
        Assert.assertEquals(job.getState(), JobDefaultStates.FINISH_STATE);
    }

    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testError() {
        Program program = new DefaultProgram();
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.processCommandResult(errorResult);
        Assert.assertEquals(job.getState(), JobDefaultStates.ERROR_STATE);
    }

    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testStop() {
        Program program = new DefaultProgram();
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.stop();
        Assert.assertEquals(job.getState(), JobDefaultStates.STOP_STATE);
    }

    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testRestart() {
        Program program = new DefaultProgram();
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.restart();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
    }

    /**
     * Test a simple job with 1 command job
     */
    @Test
    public void testOneCommand() {
        Program program = new DefaultProgram();
        program.add(new LocalCommand(Command.CommandType.COPY));
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.processCommandResult(okResult);
        Assert.assertEquals(job.getState(), JobDefaultStates.FINISH_STATE);
    }
}
