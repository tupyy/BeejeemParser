package com.beejeem.core.job;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public final class DefaultJob extends JobStateMachine implements Job {

    private final UUID id;
    private final String name;
    private final Program program;

    public DefaultJob(String name, Program program) {
        super(program, new JobStateChangeAction());

        checkNotNull(name);

        this.name = name;
        this.id = program.getID();
        this.program = program;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getID() {
        return this.id;
    }

    @Override
    public Program getProgram() {
        return this.program;
    }

    @Override
    public void start() {
        this.getStateMachine().fire(Trigger.doCommand);
    }

    @Override
    public void stop() {
        this.getStateMachine().fire(Trigger.doStop);
    }

    @Override
    public void restart() {
        this.getStateMachine().fire(Trigger.doStop);
        this.start();
    }

    @Override
    public JobState getState() {
        return null;
    }

    @Override
    public void doCommand(Command command) {

    }
}
