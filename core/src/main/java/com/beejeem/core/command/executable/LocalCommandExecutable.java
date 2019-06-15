package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.CommandResultImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public final class LocalCommandExecutable extends AbstractCommandExecutable {

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
            logger.debug("Command " + this.commandID.toString() + " output :" + out.toString());

            commandResult.setVariables(this.extractOutputVariables(out.toString()));
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.OK);
        }
        catch (ExternalProcessFailureException ex) {
            logger.error("Command " + this.commandID.toString() + " failed: " + ex.getMessage());
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.ERROR);
            commandResult.setMessage(ex.getMessage());
        }
        return commandResult;
    }




}
