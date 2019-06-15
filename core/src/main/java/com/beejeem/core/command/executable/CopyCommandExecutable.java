package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.CommandResultImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class CopyCommandExecutable extends AbstractCommandExecutable {

    private final static Logger logger = LogManager.getLogger(CopyCommandExecutable.class);
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
    public CommandResult execute() {
        CommandResult commandResult = new CommandResultImpl(this.jobID, this.commandID);
        try {

            Files.copy(source, destination.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.OK);
            logger.debug(String.format("Command %s: File copied %s -> %s", this.commandID.toString(), source.toString(), destination.toString()));
        } catch (IOException e) {
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.ERROR);
            commandResult.setMessage(e.getMessage());
            logger.error(String.format("Command %s failed. Reason: %s", this.commandID.toString(), e.getMessage()));
        }
        return commandResult;
    }
}
