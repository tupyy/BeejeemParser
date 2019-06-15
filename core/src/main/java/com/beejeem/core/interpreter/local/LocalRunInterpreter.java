package com.beejeem.core.interpreter.local;

import com.beejeem.core.executable.CommandExecutable;
import com.beejeem.core.executable.LocalCommandExecutable;
import com.beejeem.core.interpreter.AbstractInterpreter;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;
import org.buildobjects.process.ProcBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Creates a local executable for a simple command.
 * It adds the variables as environment variables. If the system is linux it will run under bash.
 */
public class LocalRunInterpreter extends AbstractInterpreter {

    @Override
    public CommandExecutable interpret(Command command, List<Variable> variables) {
        ProcBuilder procBuilder;
        Map<String,String> envVariables = this.getEnvVariables(variables);

        if (this.get_os_type() == OS_TYPE.WINDOWS) {
            procBuilder = new ProcBuilder("cmd.exe")
                    .withArgs("/c", String.valueOf(command.getVariables().get(0).getValue())).withNoTimeout();
        } else {
            procBuilder = new ProcBuilder("bash")
                    .withArgs("-c", String.valueOf(command.getVariables().get(0).getValue()))
                    .withNoTimeout();
        }

        // Adds variables as environment variables
        for (Map.Entry<String,String> var: envVariables.entrySet()) {
            procBuilder.withVar(var.getKey(),var.getValue());
        }

        return new LocalCommandExecutable(command.getParentID(), command.getID(), procBuilder);
    }

    private Map<String, String> getEnvVariables(List<Variable> variables) {
        Map<String, String> envVariables = variables.stream()
                .collect(Collectors.toMap(Variable::getName, var -> String.valueOf(var.getValue())));
        return envVariables;
    }

}
