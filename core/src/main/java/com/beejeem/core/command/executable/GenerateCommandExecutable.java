package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.ResultParser;
import com.hubspot.jinjava.Jinjava;

import java.util.Map;
import java.util.UUID;

public final class GenerateCommandExecutable implements LocalCommandExecutable {

    private final String templateName;
    private final Map<String, Object> context;
    private final UUID jobID;
    private final UUID commandID;

    public GenerateCommandExecutable(UUID jobID, UUID commandID, String templateName, Map<String,Object> context) {
        this.jobID = jobID;
        this.commandID = commandID;
        this.templateName = templateName;
        this.context = context;
    }
    @Override
    public CommandResult get() {

        Jinjava jinjava = new Jinjava();

        return null;
    }

    @Override
    public void setOutputParser(ResultParser parser) {

    }
}
