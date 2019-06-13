package com.beejeem.core.job;

import com.beejeem.core.result.CommandResult;
import com.beejeem.parser.domain.DefaultProgram;
import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.commands.LocalCommand;
import com.beejeem.parser.domain.variables.StringVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestJob {

    private DefaultJob job;
    private LocalCommand testCommand1;
    private LocalCommand testCommand2;
    private TestCommandResult okResult1, okResult2;
    private TestCommandResult errorResult2, errorResult1;
    private TestCommandAction testCommandAction = new TestCommandAction();

    @Before
    public void initObjects() {
        testCommand1 = new LocalCommand(Command.CommandType.COPY);
        testCommand2 = new LocalCommand(Command.CommandType.COPY);
        okResult1 = new TestCommandResult(testCommand1.getID(), CommandResult.CommandResultStatus.OK);
        okResult2 = new TestCommandResult(testCommand2.getID(), CommandResult.CommandResultStatus.OK);
        errorResult2 = new TestCommandResult(testCommand2.getID(), CommandResult.CommandResultStatus.ERROR);
        errorResult1 = new TestCommandResult(testCommand1.getID(), CommandResult.CommandResultStatus.ERROR);
    }
    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testNominal() {
        Program program = new DefaultProgram();
        program.add(this.testCommand1);
        program.add(this.testCommand2);
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.processCommandResult(this.okResult1);
        Assert.assertEquals(job.getState(), program.getCommands().get(1).getID());
        job.processCommandResult(this.okResult2);
        Assert.assertEquals(job.getState(), JobDefaultStates.FINISH_STATE);
    }

    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testError() {
        Program program = new DefaultProgram();
        program.add(this.testCommand1);
        program.add(this.testCommand2);
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.processCommandResult(this.errorResult1);
        Assert.assertEquals(job.getState(), JobDefaultStates.ERROR_STATE);
    }

    /**
     * Test a simple 2 commands job with ok results
     */
    @Test
    public void testStop() {
        Program program = new DefaultProgram();
        program.add(this.testCommand1);
        program.add(this.testCommand2);
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
        program.add(this.testCommand1);
        program.add(this.testCommand2);
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
        program.add(this.testCommand1);
        program.add(new StringVariable("arg1","value"));

        job = new DefaultJob("test", program, testCommandAction);
        Assert.assertEquals(job.getState(), JobDefaultStates.READY_STATE);
        job.start();
        Assert.assertEquals(job.getState(), program.getCommands().get(0).getID());
        job.processCommandResult(this.okResult1);
        Assert.assertEquals(job.getState(), JobDefaultStates.FINISH_STATE);
    }
}
