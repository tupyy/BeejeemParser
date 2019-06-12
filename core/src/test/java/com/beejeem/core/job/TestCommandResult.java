package com.beejeem.core.job;

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
}
