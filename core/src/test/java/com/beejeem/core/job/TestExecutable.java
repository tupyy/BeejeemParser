package com.beejeem.core.job;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.CopyCommandExecutable;
import com.beejeem.core.command.executable.LocalCommandExecutable;
import com.beejeem.core.command.interpreter.DefaultCommandInterpreter;
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
        CommandExecutable commandExecutable = new DefaultCommandInterpreter().interpret(copyCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof CopyCommandExecutable);

        CommandResult result = commandExecutable.execute();
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.OK);
        Assert.assertTrue(Files.exists(Paths.get(this.tempFolderDest.toString(),tempSourceFile.getFileName().toString())));
    }

    @Test
    public void testCopyCommandKO() {

        Command copyCommand = new LocalCommand(Command.CommandType.COPY);
        // add args
        copyCommand.add(new StringVariable("source", "unknown_file"));
        copyCommand.add(new StringVariable("destination", this.tempFolderDest.toString()));
        CommandExecutable commandExecutable = new DefaultCommandInterpreter().interpret(copyCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof CopyCommandExecutable);

        CommandResult result = commandExecutable.execute();
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.ERROR);
    }

    @Test
    public void TestRunCommandOK() {

        Command runCommand = new LocalCommand(Command.CommandType.RUN);
        // add args
        runCommand.add(new StringVariable("echo", "echo meee"));
        CommandExecutable commandExecutable = new DefaultCommandInterpreter().interpret(runCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof LocalCommandExecutable);

        CommandResult result = commandExecutable.execute();
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.OK);
    }

    @Test
    public void TestRunCommandKO() {

        Command runCommand = new LocalCommand(Command.CommandType.RUN);
        // add args
        runCommand.add(new StringVariable("echo", "foo"));
        CommandExecutable commandExecutable = new DefaultCommandInterpreter().interpret(runCommand, new ArrayList<>());
        Assert.assertTrue(commandExecutable instanceof LocalCommandExecutable);

        CommandResult result = commandExecutable.execute();
        Assert.assertEquals(result.getResultStatus(), CommandResult.CommandResultStatus.ERROR);
    }
}
