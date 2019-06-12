package com.beejeem.core.job;

public class TestCommandResult implements CommandResult {

    private final CommandResultStatus status;

    public TestCommandResult(CommandResultStatus status) {
        this.status = status;
    }
    @Override
    public CommandResultStatus getResultStatus() {
        return status;
    }
}
