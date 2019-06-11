package com.beejeem.core.job;

import com.beejeem.parser.domain.commands.Command;
import com.github.oxo42.stateless4j.delegates.Action;

public class DefaultCommandAction implements Action {
    private final Command command;

    public DefaultCommandAction(Command command) {
        this.command = command;
    }

    @Override
    public void doIt() {

    }
}
