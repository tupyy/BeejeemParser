package com.beejeem.core.command.interpreter.local;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.RunCommandExecutable;
import com.beejeem.core.command.interpreter.AbstractInterpreter;
import com.beejeem.core.command.result.JsonResultParser;
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

        CommandExecutable runCommandExecutable = new RunCommandExecutable(command.getParentID(), command.getID(), procBuilder);
        runCommandExecutable.setOutputParser(new JsonResultParser());
        return runCommandExecutable;
    }

    private Map<String, String> getEnvVariables(List<Variable> variables) {
        Map<String, String> envVariables = variables.stream()
                .collect(Collectors.toMap(Variable::getName, var -> String.valueOf(var.getValue())));
        return envVariables;
    }

}
