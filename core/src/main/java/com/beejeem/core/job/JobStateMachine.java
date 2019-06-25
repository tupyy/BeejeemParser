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

    // Trigger definitions
    protected enum Trigger {
        DO_COMMAND,
        DO_ERROR,
        DO_FINISH,
        DO_RESTART,
        DO_STOP
    }

    /**
     * Definition of default states. These states are common to every job
     */
    public static final UUID STOP_STATE = UUID.randomUUID();
    public static final UUID ERROR_STATE = UUID.randomUUID();
    public static final UUID FINISH_STATE = UUID.randomUUID();
    public static final UUID READY_STATE = UUID.randomUUID();

    // state machine
    private final StateMachine<UUID, Trigger> stateMachine;

    //Trigger definition for command states
    private TriggerWithParameters2<Command, List, UUID, Trigger> commandTrigger
            = new TriggerWithParameters2<>(Trigger.DO_COMMAND, Command.class, List.class);

    /**
     * Action called at entry in a new command state.
     * @see {@link Action2}
     */
    private final Action2 commandAction;

    /**
     * Action called when state changes.
     * @see {@link Action}
     */
    private final Action changeStateAction;

    @SuppressWarnings("unchecked")
    public JobStateMachine(Program program, Action changeStateAction, Action2 commandAction) {

        checkNotNull(program);

        // Allow at least one command
        checkElementIndex(0, program.getCommands().size());
        checkNotNull(changeStateAction);
        checkNotNull(commandAction);

        this.commandAction = commandAction;
        this.changeStateAction = changeStateAction;

        StateMachineConfig stateMachineConfig = this.createStateMachineConfiguration(program);
        this.stateMachine = new StateMachine<>(READY_STATE, stateMachineConfig);
    }

    /**
     * Get the state machine
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

        if (commands.size() == 1) {
            /*
             * Only one command in program hence the first state will be the last
             */
            stateMachineConfig.configure(commands.get(0).getID())
                    .onEntry(changeStateAction)
                    .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                    .permit(Trigger.DO_STOP, STOP_STATE)
                    .permit(Trigger.DO_ERROR, ERROR_STATE)
                    .permitReentry(Trigger.DO_RESTART)
                    .permit(Trigger.DO_COMMAND, FINISH_STATE)
                    .permit(Trigger.DO_FINISH, FINISH_STATE);
        } else {
            for (int i = 0; i < commands.size(); i++) {
                if (i == 0) {
                    /*
                     * Configure the first state. Permit the reentry in the case of restart trigger
                     */
                    stateMachineConfig.configure(commands.get(i).getID())
                            .onEntry(changeStateAction)
                            .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                            .permit(Trigger.DO_STOP, STOP_STATE)
                            .permit(Trigger.DO_ERROR, ERROR_STATE)
                            .permitReentry(Trigger.DO_RESTART)
                            .permit(Trigger.DO_COMMAND, commands.get(i + 1).getID());
                } else if (i < commands.size() - 1) {
                    stateMachineConfig.configure(commands.get(i).getID())
                            .onEntry(changeStateAction)
                            .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                            .permit(Trigger.DO_STOP, STOP_STATE)
                            .permit(Trigger.DO_ERROR, ERROR_STATE)
                            .permit(Trigger.DO_RESTART, firstStageID)
                            .permit(Trigger.DO_COMMAND, commands.get(i + 1).getID());
                } else {
                    /*
                     * Last state. The trigger DO_COMMAND will transit to finish state
                     */
                    stateMachineConfig.configure(commands.get(i).getID())
                            .onEntry(changeStateAction)
                            .onEntryFrom(commandTrigger, commandAction, Command.class, List.class)
                            .permit(Trigger.DO_STOP, STOP_STATE)
                            .permit(Trigger.DO_ERROR, ERROR_STATE)
                            .permit(Trigger.DO_RESTART, firstStageID)
                            .permit(Trigger.DO_COMMAND, FINISH_STATE)
                            .permit(Trigger.DO_FINISH, FINISH_STATE);
                }
            }
        }

        //add stop state configuration
        stateMachineConfig.configure(STOP_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.DO_COMMAND, firstStageID);

        //add error state configuration
        stateMachineConfig.configure(ERROR_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.DO_COMMAND,firstStageID);

        // add start state configuration. Permits trigger for the first command of the program
        stateMachineConfig.configure(READY_STATE)
                .onEntry(changeStateAction)
                .permit(Trigger.DO_COMMAND, firstStageID);

        return stateMachineConfig;

    }
}
