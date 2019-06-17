package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.ResultParser;

import java.util.function.Supplier;

public interface CommandExecutable extends Supplier<CommandResult> {

    public void setOutputParser(ResultParser parser);

}
