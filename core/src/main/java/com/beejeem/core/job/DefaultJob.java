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

    //it holds the current copy of the program when the job is running
    private Program clone;

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
        if (this.getState() == READY_STATE ||
                this.getState() == STOP_STATE) {
            this.clone = this.program.clone();
            this.getStateMachine().fire(this.getCommandTrigger(), this.clone.getIterator().next(), this.clone.getVariables());
        }
    }

    @Override
    public void stop() {
        if (this.getState() != STOP_STATE ||
                this.getState() != READY_STATE) {
            this.getStateMachine().fire(Trigger.DO_STOP);
        }
    }

    @Override
    public void restart() {
        if (this.getState() != READY_STATE) {
            this.getStateMachine().fire(Trigger.DO_RESTART);
        }
    }

    @Override
    public UUID getState() {
        return this.getStateMachine().getState();
    }

    @Override
    public void processCommandResult(CommandResult result) throws IllegalAccessException {

        // result command id must match the current state = command id
        if (result.getCommandID() != this.getStateMachine().getState()) {
            throw new IllegalAccessException("Result ID do not match the current command ID.");
        }

        if (result.getResultStatus() == CommandResult.CommandResultStatus.OK) {
            if (this.clone.getIterator().hasNext()) {
                Command nextCommand = this.clone.getIterator().next();
                this.getStateMachine().fire(this.getCommandTrigger(), nextCommand, this.clone.getVariables());
            } else {
                this.getStateMachine().fire(Trigger.DO_FINISH);
            }
        } else {
            this.getStateMachine().fire(Trigger.DO_ERROR);
        }
    }

}
