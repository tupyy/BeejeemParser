package com.beejeem.core.job;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.CopyCommandExecutable;
import com.beejeem.core.command.executable.RunCommandExecutable;
import com.beejeem.core.command.interpreter.CommandInterpreterImpl;
import com.beejeem.core.command.result.CommandResult;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.commands.LocalCommand;
import com.beejeem.parser.domain.variables.StringVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestExecutable {

    private Path tempFolderDest;
    private Path tempSourceFile;

    @Before
    public void initTemporaryFolders() throws IOException {
        tempFolderDest = Files.createTempDirectory("destinationFolder");
        tempSourceFile = Files.createTempFile("tempFile", ".tmp");
    }

    @Test
    public void testCopyCommandOK() {
        Command copyCommand = new LocalCommand(Command.CommandType.COPY);
        // add args
        copyCommand.add(new StringVariable("source", this.tempSourceFile.toString()));
        copyCommand.add(new StringVariable("destination", this.tempFolderDest.toString()));
        CommandExecutable commandExecutable = new CommandInterpreterImpl().interpret(copyCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof CopyCommandExecutable);

        CommandResult result = null;
        try {
            result = commandExecutable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.OK);
        Assert.assertTrue(Files.exists(Paths.get(this.tempFolderDest.toString(),tempSourceFile.getFileName().toString())));
    }

    @Test
    public void testCopyCommandKO() {

        Command copyCommand = new LocalCommand(Command.CommandType.COPY);
        // add args
        copyCommand.add(new StringVariable("source", "unknown_file"));
        copyCommand.add(new StringVariable("destination", this.tempFolderDest.toString()));
        CommandExecutable commandExecutable = new CommandInterpreterImpl().interpret(copyCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof CopyCommandExecutable);

        CommandResult result = null;
        try {
            result = commandExecutable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.ERROR);
    }

    @Test
    public void TestRunCommandOK() {

        Command runCommand = new LocalCommand(Command.CommandType.RUN);
        // add args
        runCommand.add(new StringVariable("echo", "python3 /home/cosmin/Projects/BeejeemParser/core/src/test/java/com/beejeem/core/job/python_test.py"));
        CommandExecutable commandExecutable = new CommandInterpreterImpl().interpret(runCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof RunCommandExecutable);

        CommandResult result = null;
        try {
            result = commandExecutable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(result.getOutputVariables().size(), 3);
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.OK);
    }

    @Test
    public void TestRunCommandKO() {

        Command runCommand = new LocalCommand(Command.CommandType.RUN);
        // add args
        runCommand.add(new StringVariable("echo", "foo"));
        CommandExecutable commandExecutable = new CommandInterpreterImpl().interpret(runCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof RunCommandExecutable);

        CommandResult result = null;
        try {
            result = commandExecutable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.ERROR);
    }
}
