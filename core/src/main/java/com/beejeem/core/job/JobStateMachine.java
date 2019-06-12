package com.beejeem.core.job;

import com.beejeem.parser.domain.Program;
import com.beejeem.parser.domain.commands.Command;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;
import com.github.oxo42.stateless4j.delegates.Action2;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters2;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class JobStateMachine {

    protected enum Trigger {
        doCommand,
        doError,
        doFinish,
        doRestart,
        doStop
    }

    private final StateMachine<UUID, Trigger> stateMachine;
    private TriggerWithParameters2<Command, List, UUID, Trigger> commandTrigger
            = new TriggerWithParameters2<>(Trigger.doCommand, Command.class, List.class);

    private final Action2 commandAction;
    private final Action changeStateAction;

    @SuppressWarnings("unchecked")
    public JobStateMachine(Program program, Action changeStateAction, Action2 commandAction) {

        checkNotNull(program);

        // Allow at least one command
        checkElementIndex(1, program.getCommands().size());
        checkNotNull(changeStateAction);
        checkNotNull(commandAction);

        this.commandAction = commandAction;
        this.changeStateAction = changeStateAction;

        StateMachineConfig stateMachineConfig = this.createStateMachineConfiguration(program);
        this.stateMachine = new StateMachine<UUID, Trigger>(JobDefaultStates.READY_STATE, stateMachineConfig);
    }

    /**
     * Get the state machine
     *
     * @return state machine
     */
    protected StateMachine<UUID, Trigger> getStateMachine() {
        return stateMachine;
    }

    /**
     * Return the command trigger
     */
    protected TriggerWithParameters2 getCommandTrigger() {
        return this.commandTrigger;
    }

    /**
     * Create the configuration of the state machine based on the job program.
     * For each command, a state is created. Each state will permit the transition to next command except for the last
     * command which will transit to FINISH_STATE.
     * Common states like START, STOP, RESTART, ERROR are also added.
     *
     * @param program job program
     * @return state machine configuration
     */
    @SuppressWarnings("unchecked")
    private StateMachineConfig createStateMachineConfiguration(Program program) {
        StateMachineConfig<UUID, Trigger> stateMachineConfig = new StateMachineConfig<>();

        // define trigger for commands. It a trigger taking 2 arguments: command object and the list of variables

        List<Command> commands = program.getCommands();
        UUID firstStageID = commands.get(0).getID();

        for (int i = 0; i < commands.size(); i++) {
            if (i == 0) {
                /*
                 * Configure the first stage. Permit the reentry in the case of restart trigger
                 */
                stateMachineConfig.configure(commands.get(i).getID())
                        .onEntry(changeStateAction)
                        .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                        .permit(Trigger.doStop, JobDefaultStates.STOP_STATE)
                        .permit(Trigger.doError, JobDefaultStates.ERROR_STATE)
                        .permitReentry(Trigger.doRestart)
                        .permit(Trigger.doCommand, commands.get(i + 1).getID());
            } else if (i < commands.size() - 1) {
                stateMachineConfig.configure(commands.get(i).getID())
                        .onEntry(changeStateAction)
                        .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                        .permit(Trigger.doStop, JobDefaultStates.STOP_STATE)
                        .permit(Trigger.doError, JobDefaultStates.ERROR_STATE)
                        .permit(Trigger.doRestart, firstStageID)
                        .permit(Trigger.doCommand, commands.get(i + 1).getID());
            } else {
                /*
                 * Last stage. The trigger doCommand will transit to finish state
                 */
                stateMachineConfig.configure(commands.get(i).getID())
                        .onEntry(changeStateAction)
                        .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                        .permit(Trigger.doStop, JobDefaultStates.STOP_STATE)
                        .permit(Trigger.doError, JobDefaultStates.ERROR_STATE)
                        .permit(Trigger.doRestart, firstStageID)
                        .permit(Trigger.doCommand, JobDefaultStates.FINISH_STATE);
            }
        }

        //add stop state configuration
        stateMachineConfig.configure(JobDefaultStates.STOP_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.doCommand, firstStageID);

        //add error state configuration
        stateMachineConfig.configure(JobDefaultStates.ERROR_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.doCommand,firstStageID);

        // add start state configuration. Permits trigger for the first command of the program
        stateMachineConfig.configure(JobDefaultStates.READY_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.doCommand, firstStageID);

        return stateMachineConfig;

    }
}
