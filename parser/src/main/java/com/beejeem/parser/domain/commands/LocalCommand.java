package com.beejeem.parser.domain.commands;

import java.util.UUID;

public class LocalCommand extends AbstractCommand {

    public LocalCommand(UUID parentID, CommandType type) {
        super(parentID, type);
    }

    public LocalCommand(CommandType type) {
        super(type);
    }

    @Override
    public Boolean isRemote() {
        return false;
    }
}
