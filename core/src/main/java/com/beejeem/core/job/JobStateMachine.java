package com.beejeem.core.job;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class JobStateMachine {

    protected enum Trigger {
        doCommand,
        doError,
        doFinish,
        doRestart,
        doStart,
        doStop
    }

    public final static UUID STOP_STATE = UUID.randomUUID();
    public final static UUID RESTART_STATE = UUID.randomUUID();
    public final static UUID ERROR_STATE = UUID.randomUUID();
    public final static UUID FINISH_STATE = UUID.randomUUID();
    public final static UUID READY_STATE = UUID.randomUUID();

    private final StateMachine<UUID,Trigger> stateMachine;

    private final Action changeStateAction;

    public JobStateMachine(Program program, Action changeStateAction) {

        checkNotNull(program);
        checkNotNull(changeStateAction);

        this.changeStateAction = changeStateAction;
        StateMachineConfig stateMachineConfig = this.createStateMachineConfiguration(program);
        this.stateMachine = new StateMachine<UUID, Trigger>(READY_STATE,stateMachineConfig);
    }

    /**
     * Get the state machine
     * @return state machine
     */
    protected StateMachine<UUID, Trigger> getStateMachine() {
        return stateMachine;
    }

    /**
     * Method called when entry into a command state
     * @param command command to be executed
     */
    public abstract void doCommand(Command command);

    /**
     * Create the configuration of the state machine based on the job program.
     * For each command, a state is created. Each state will permit the transition to next command except for the last
     * command which will transit to FINISH_STATE.
     * Common states like START, STOP, RESTART, ERROR are also added.
     * @param program job program
     * @return state machine configuration
     */
    private StateMachineConfig createStateMachineConfiguration(Program program) {
        StateMachineConfig<UUID, Trigger> stateMachineConfig = new StateMachineConfig<>();
        List<Command> commands = program.getCommands();
        for (int i = 0; i < commands.size(); i++) {
            Command currentCommand = commands.get(i);
            if (i < commands.size() - 1) {
                stateMachineConfig.configure(commands.get(i).getID())
                        .onEntry(changeStateAction)
                        .onEntry(() -> this.doCommand(currentCommand))
                        .permit(Trigger.doStop, STOP_STATE)
                        .permit(Trigger.doError, ERROR_STATE)
                        .permit(Trigger.doRestart, RESTART_STATE)
                        .permit(Trigger.doCommand, commands.get(i + 1).getID());
            } else {
                stateMachineConfig.configure(commands.get(i).getID())
                        .onEntry(changeStateAction)
                        .onEntry(() -> this.doCommand(currentCommand))
                        .permit(Trigger.doStop, STOP_STATE)
                        .permit(Trigger.doError, ERROR_STATE)
                        .permit(Trigger.doRestart, RESTART_STATE)
                        .permit(Trigger.doFinish, FINISH_STATE);
            }
        }

        //add stop state configuration
        stateMachineConfig.configure(STOP_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.doStart, READY_STATE);

        //add error state configuration
        stateMachineConfig.configure(ERROR_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.doStart, READY_STATE);

        // add start state configuration. Permits trigger for the first command of the program
        stateMachineConfig.configure(READY_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.doStop, STOP_STATE)
                .permit(Trigger.doError, ERROR_STATE)
                .permit(Trigger.doCommand, program.getCommands().get(0).getID());


        return stateMachineConfig;

    }
}
