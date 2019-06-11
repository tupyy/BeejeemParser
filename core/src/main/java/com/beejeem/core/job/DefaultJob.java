package com.beejeem.core.job;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;

import java.util.UUID;

public final class DefaultJob extends JobStateMachine implements Job {

    private final UUID id = UUID.randomUUID();
    private final String name;
    private final Program program;

    public DefaultJob(String name, Program program) {
        super(program, new JobStateChangeAction());
        this.name = name;
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
