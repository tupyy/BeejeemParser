package com.beejeem.core.interpreter.local;

import com.beejeem.core.executable.CommandExecutable;
import com.beejeem.core.executable.LocalCommandExecutable;
import com.beejeem.core.interpreter.AbstractInterpreter;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;
import org.buildobjects.process.ProcBuilder;

import java.util.List;

public class CopyInterpreter extends AbstractInterpreter {

    public CopyInterpreter() {
    }

    @Override
    public CommandExecutable interpret(Command command, List<Variable> variables) {
        ProcBuilder procBuilder = new ProcBuilder(this.getCommand(this.get_os_type()))
                .withArg(String.valueOf(command.getVariables().get(0).getValue()))
                .withArg(String.valueOf(command.getVariables().get(1).getValue()));

        return new LocalCommandExecutable(procBuilder);
    }

    private String getCommand(OS_TYPE os_type) {
        return os_type == OS_TYPE.WINDOWS ? "copy" : "cp";
    }
}
