package com.beejeem.core.command.executable;

import com.beejeem.core.command.result.CommandResult;
import com.beejeem.core.command.result.ResultParser;

import java.util.concurrent.Callable;

public interface CommandExecutable extends Callable<CommandResult> {

    public void setOutputParser(ResultParser parser);

}
