package com.beejeem.core.executor;

import com.beejeem.core.command.executable.CommandExecutable;
import com.beejeem.core.command.executable.LocalCommandExecutable;
import com.beejeem.core.command.result.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public final class CommandExecutorImpl implements CommandExecutor<CommandExecutable> {

    private static Logger logger = LogManager.getLogger(CommandExecutorImpl.class);

    /**
     * Create an executor for local jobs.
     */
    private ThreadPoolExecutor localExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    /**
     * Create a remote executor. This solution is used because there are problems with ssh session if
     * more than 2 jobs are executed in parallel.
     * TODO Find a better solution for executing remote commands
     */
    private ThreadPoolExecutor remoteExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    @Autowired
    private CommandResultManager commandResultManager;

    public CommandExecutorImpl() {
    }

    @Override
    public void submit(CommandExecutable executable) {
        CompletableFuture<CommandResult> result;
        if (executable instanceof LocalCommandExecutable) {
             logger.info(String.format("Submitting command <%s> of job <%s> to local executor", executable.getCommandID(), executable.getJobID()));
             result = (CompletableFuture<CommandResult>) localExecutor.submit(executable);
        } else {
            logger.info(String.format("Submitting command <%s> of job <%s> to remote executor", executable.getCommandID(), executable.getJobID()));
            result = (CompletableFuture<CommandResult>) remoteExecutor.submit(executable);
        }
        result.thenAccept(this.commandResultManager::setResult);
    }
}
