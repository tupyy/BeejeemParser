package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.CommandResultImpl;
import com.beejeem.core.command.result.ResultParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Execute a copy command. It do not create the destination folder.
 * If the destination folder is not found, it returns an
 * {@link com.beejeem.core.command.result.CommandResult.CommandResultStatus} Error
 */
public class CopyCommandExecutable implements LocalCommandExecutable {

    private static final Logger logger = LogManager.getLogger(CopyCommandExecutable.class);
    private final UUID jobID;
    private final UUID commandID;
    private final Path source;
    private final Path destination;
    public CopyCommandExecutable(UUID jobID, UUID commandID, Path source, Path destination) {
        this.jobID = jobID;
        this.commandID = commandID;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public CommandResult call() {
        CommandResult commandResult = new CommandResultImpl(this.jobID, this.commandID);
        try {
            Files.copy(source, destination.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.OK);
            logger.debug("Command {}: File copied {} -> {}", this.commandID, source, destination);
        } catch (IOException e) {
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.ERROR);
            commandResult.setMessage(e.getMessage());
            logger.error("Command {} failed. Reason: {}", this.commandID, e.getMessage());
        }
        return commandResult;
    }

    @Override
    public UUID getCommandID() {
        return this.commandID;
    }

    @Override
    public UUID getJobID() {
        return this.jobID;
    }

    @Override
    public void setOutputParser(ResultParser parser) {
       throw new UnsupportedOperationException("Parser is not used here.");
    }

}
