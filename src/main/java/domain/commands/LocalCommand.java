package domain.commands;

import java.util.UUID;

public class LocalCommand extends AbstractCommand {

    public LocalCommand(UUID parentID) {
        super(parentID);
    }

    @Override
    public Boolean isRemote() {
        return false;
    }
}
