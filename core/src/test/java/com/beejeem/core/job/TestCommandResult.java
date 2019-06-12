package com.beejeem.core.job;

import com.beejeem.core.result.CommandResult;

import java.util.UUID;

public class TestCommandResult implements CommandResult {

    private final CommandResultStatus status;

    public TestCommandResult(CommandResultStatus status) {
        this.status = status;
    }

    @Override
    public CommandResultStatus getResultStatus() {
        return status;
    }

    @Override
    public UUID getCommandID() {
        return null;
    }

    @Override
    public UUID getJobID() {
        return null;
    }
}
