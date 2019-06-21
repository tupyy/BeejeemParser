package com.beejeem.core.executor;

import com.beejeem.core.CoreImpl;
import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.LocalCommandExecutable;
import com.beejeem.core.command.result.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;

public final class CommandExecutorImpl implements CommandExecutor<CommandExecutable> {

    private static Logger logger = LogManager.getLogger(CommandExecutorImpl.class);

    private final CommandResultManager commandResultManager;

    public CommandExecutorImpl(CommandResultManager commandResultManager) {
        checkNotNull(commandResultManager, "Command result manager cannot be null");
        this.commandResultManager = commandResultManager;
    }

    @Override
    public void submit(CommandExecutable executable) {
        CompletableFuture<CommandResult> result;
        if (executable instanceof LocalCommandExecutable) {
             logger.info(String.format("Submitting command <%s> of job <%s> to local executor", executable.getCommandID(), executable.getJobID()));
             result = (CompletableFuture<CommandResult>) CoreImpl.getInstance().getLocalExecutor().submit(executable);
        } else {
            logger.info(String.format("Submitting command <%s> of job <%s> to remote executor", executable.getCommandID(), executable.getJobID()));
            result = (CompletableFuture<CommandResult>) CoreImpl.getInstance().getRemoteExecutor().submit(executable);
        }
        result.thenAccept(this.commandResultManager::setResult);
    }
}
