package com.beejeem.core.executable;

import com.beejeem.core.result.CommandResult;
import com.beejeem.core.result.CommandResultImpl;
import com.beejeem.core.result.JsonExtractor;
import com.beejeem.parser.domain.variables.Variable;
import com.beejeem.parser.parser.JsonParser;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
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
            logger.debug("Command " + this.commandID.toString() + " output :" + out.toString());

            commandResult.setVariables(this.extractOutputVariables(out.toString()));
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.OK);
        }
        catch (ExternalProcessFailureException ex) {
            logger.error("Command " + this.commandID.toString() + " failed: " + ex.getMessage());
            commandResult.setCommandResultStatus(CommandResult.CommandResultStatus.ERROR);
        }
        return commandResult;
    }

    private List<Variable> extractOutputVariables(String output) {
        JsonParser jsonParser = new JsonParser();
        try {
            return jsonParser.parse(JsonExtractor.extract(output));
        }
        catch (ParseCancellationException ex) {
            logger.error(ex.getMessage());
            return new ArrayList<>();
        }
    }
}
