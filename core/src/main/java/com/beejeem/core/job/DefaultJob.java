package com.beejeem.core.job;

import com.beejeem.core.job.actions.JobStateChangeAction;
import com.beejeem.core.command.result.CommandResult;
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
        if (this.getState() == JobDefaultStates.READY_STATE ||
                this.getState() == JobDefaultStates.STOP_STATE) {
            this.getStateMachine().fire(this.getCommandTrigger(), program.getIterator().next(), program.getVariables());
        }
    }

    @Override
    public void stop() {
        if (this.getState() != JobDefaultStates.STOP_STATE ||
                this.getState() != JobDefaultStates.READY_STATE) {
            this.getStateMachine().fire(Trigger.doStop);
        }
    }

    @Override
    public void restart() {
        if (this.getState() != JobDefaultStates.READY_STATE) {
            this.getStateMachine().fire(Trigger.doRestart);
        }
    }

    @Override
    public UUID getState() {
        return this.getStateMachine().getState();
    }

    @Override
    public void processCommandResult(CommandResult result) {

        // result command id must match the current state = command id
        assert (result.getCommandID() == this.getStateMachine().getState());

        if (result.getResultStatus() == CommandResult.CommandResultStatus.OK) {
            if (program.getIterator().hasNext()) {
                Command nextCommand = program.getIterator().next();
                this.getStateMachine().fire(this.getCommandTrigger(), nextCommand, program.getVariables());
            } else {
                this.getStateMachine().fire(Trigger.doFinish);
            }
        } else {
            this.getStateMachine().fire(Trigger.doError);
        }
    }

}
