package com.beejeem.core.executor;

public interface CommandExecutor<T> {

    public void submit(T executable);
}
