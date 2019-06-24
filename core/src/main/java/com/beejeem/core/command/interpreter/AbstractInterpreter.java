package com.beejeem.core.command.interpreter;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.parser.domain.commands.Command;
import com.beejeem.parser.domain.variables.Variable;

import java.util.List;

public abstract class AbstractInterpreter implements CommandInterpreter<CommandExecutable> {

    public enum OS_TYPE {
        LINUX,
        WINDOWS,
        OSX,
        UNKNWON
    }

    @Override
    public abstract CommandExecutable interpret(Command command, List<Variable> variables);

    public OS_TYPE get_os_type() {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("nix") || os.contains("nux")) {
            return OS_TYPE.LINUX;
        } else if (os.contains("win")) {
            return OS_TYPE.WINDOWS;
        } else if (os.contains("mac")) {
            return OS_TYPE.OSX;
        }
        return OS_TYPE.UNKNWON;
    }

}
