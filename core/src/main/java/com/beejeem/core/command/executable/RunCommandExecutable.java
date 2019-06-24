package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.CommandResultImpl;
import com.beejeem.core.command.result.ResultParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public final class RunCommandExecutable implements LocalCommandExecutable {

    private static Logger logger = LogManager.getLogger(RunCommandExecutable.class);

    private final ProcBuilder procBuilder;
    private final UUID jobID;
    private final UUID commandID;
    private ResultParser outputParser;


    public RunCommandExecutable(UUID jobID, UUID commandID, ProcBuilder procBuilder) {
        this.jobID = jobID;
        this.commandID = commandID;
        this.procBuilder = procBuilder;
    }

    @Override
    public CommandResult call() {
        CommandResult commandResult = new CommandResultImpl(this.jobID, this.commandID);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            this.procBuilder.withOutputStream(out).run();
            logger.debug("Command " + this.commandID + " output :" + out);

            if (this.getOutputParser() != null) {
                commandResult.setVariables(this.getOutputParser().parse(out.toString()));
            }
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.OK);
        }
        catch (ExternalProcessFailureException ex) {
            logger.error("Command " + this.commandID + " failed: " + ex.getMessage());
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.ERROR);
            commandResult.setMessage(ex.getMessage());
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

    /**
     * Set output parser
     * @param outputParser output parser
     */
    @Override
    public void setOutputParser(ResultParser outputParser) {
        this.outputParser = outputParser;
    }

    /**
     * Return the output parser if any
     * @return output parser or null if not set
     */
    public ResultParser getOutputParser() {
        return outputParser;
    }
}
