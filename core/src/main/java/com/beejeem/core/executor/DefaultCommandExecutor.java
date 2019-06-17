package com.beejeem.core.executor;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.LocalCommandExecutable;
import com.beejeem.core.command.result.CommandResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.google.common.base.Preconditions.checkNotNull;

public class DefaultCommandExecutor implements CommandExecutor<CommandExecutable> {

    private final CommandResultManager commandResultManager;
    private ThreadPoolExecutor localExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    private ThreadPoolExecutor remoteExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    public DefaultCommandExecutor(CommandResultManager commandResultManager) {
        checkNotNull(commandResultManager, "Command result manager cannot be null");
        this.commandResultManager = commandResultManager;
    }

    @Override
    public void submit(CommandExecutable executable) {
        CompletableFuture<CommandResult> result;
        if (executable instanceof LocalCommandExecutable) {
             result = (CompletableFuture<CommandResult>) localExecutor.submit(executable);
        } else {
            result = (CompletableFuture<CommandResult>) remoteExecutor.submit(executable);
        }
        result.thenAccept(this.commandResultManager::setResult);
    }
}
