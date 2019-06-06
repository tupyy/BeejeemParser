package domain.commands;

import java.util.UUID;

public class LocalCommand extends AbstractCommand {

    public LocalCommand(UUID parentID, CommandType type) {
        super(parentID, type);
    }

    @Override
    public Boolean isRemote() {
        return false;
    }
}
