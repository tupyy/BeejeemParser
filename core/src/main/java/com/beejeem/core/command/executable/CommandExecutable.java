package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.ResultParser;

import java.util.UUID;
import java.util.concurrent.Callable;

public interface CommandExecutable extends Callable<CommandResult> {

    /**
     * Return command id
     * @return command id
     */
    public UUID getCommandID();

    /**
     * Return the job id
     * @return job id
     */
    public UUID getJobID();

    public void setOutputParser(ResultParser parser);

}
