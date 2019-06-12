package com.beejeem.core.executable;

import com.beejeem.core.result.CommandResult;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;

import java.util.UUID;

public class LocalCommandExecutable implements CommandExecutable {

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
        ProcResult result = this.procBuilder.run();
        return null;
    }
}
