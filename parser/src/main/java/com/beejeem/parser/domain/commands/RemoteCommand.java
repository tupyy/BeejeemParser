package com.beejeem.parser.domain.commands;

import java.util.UUID;

public class RemoteCommand extends AbstractCommand {

    public RemoteCommand(UUID parentID, CommandType type) {
        super(parentID, type);
    }

    public RemoteCommand(CommandType type) {
        super(type);
    }

    @Override
    public Boolean isRemote() {
        return true;
    }
}
