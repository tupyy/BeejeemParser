package com.beejeem.core.job;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;
import com.github.oxo42.stateless4j.delegates.Action2;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public final class DefaultJob extends JobStateMachine implements Job {

    private final UUID id;
    private final String name;
    private final Program program;

    public DefaultJob(String name, Program program, Action2 commandAction) {
        super(program, new JobStateChangeAction(), commandAction);

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
    public void processCommandResult(CommandResult result) {
        if (result.getResultStatus() == CommandResult.CommandResultStatus.OK) {
            if (program.hasNext()) {
                Command nextCommand = program.next();
                this.getStateMachine().fire(this.getCommandTrigger(),nextCommand,program.getVariables());
            }
            else {
                this.getStateMachine().fire(Trigger.doFinish);
            }
        } else {
            this.getStateMachine().fire(Trigger.doError);
        }
    }

}
