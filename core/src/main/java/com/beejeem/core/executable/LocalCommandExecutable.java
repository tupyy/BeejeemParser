package com.beejeem.core.executable;

import com.beejeem.core.job.CommandResult;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;

public class LocalCommandExecutable implements CommandExecutable {

    private final ProcBuilder procBuilder;

    public LocalCommandExecutable(ProcBuilder procBuilder) {
        this.procBuilder = procBuilder;
    }

    @Override
    public CommandResult execute() {
        ProcResult result = this.procBuilder.run();
        return null;
    }
}
