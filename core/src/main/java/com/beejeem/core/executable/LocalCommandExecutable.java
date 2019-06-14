package com.beejeem.core.executable;

import com.beejeem.core.result.CommandResult;
import com.beejeem.core.result.CommandResultImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public final class LocalCommandExecutable implements CommandExecutable {

    private static Logger logger = LogManager.getLogger(LocalCommandExecutable.class);

    private final ProcBuilder procBuilder;
    private final UUID jobID;
    private final UUID commandID;

    public LocalCommandExecutable(UUID jobID, UUID commandID,ProcBuilder procBuilder) {
        this.jobID = jobID;
        this.commandID = commandID;
        this.procBuilder = procBuilder;
    }

    @Override
    public CommandResult execute() {
        CommandResult commandResult = new CommandResultImpl(this.jobID, this.commandID);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            this.procBuilder.withOutputStream(out).run();
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.OK);
            logger.debug("Command " + this.commandID.toString() + " output :" + out.toString());
        }
        catch (ExternalProcessFailureException ex) {
            logger.error("Command " + this.commandID.toString() + " failed: " + out.toString());
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.ERROR);
        }
        return commandResult;
    }
}
