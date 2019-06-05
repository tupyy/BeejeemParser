package domain.commands;

import java.util.UUID;

public class RemoteCommand extends AbstractCommand {

    public RemoteCommand(UUID parentID) {
        super(parentID);
    }

    @Override
    public Boolean isRemote() {
        return true;
    }
}
