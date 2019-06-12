package com.beejeem.core.job;

import com.beejeem.parser.domain.commands.Command;
import com.github.oxo42.stateless4j.delegates.Action2;

import java.util.List;

public class TestCommandAction implements Action2<Command, List> {

    @Override
    public void doIt(Command command, List list) {
        System.out.println("Execute command :" + command.getID().toString());
    }
}
