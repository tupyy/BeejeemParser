package com.beejeem.core.job;

import java.util.UUID;

public final class JobDefaultStates {

    public static final UUID STOP_STATE = UUID.randomUUID();
    public static final UUID ERROR_STATE = UUID.randomUUID();
    public static final UUID FINISH_STATE = UUID.randomUUID();
    public static final UUID READY_STATE = UUID.randomUUID();

    public JobDefaultStates() {}
}
